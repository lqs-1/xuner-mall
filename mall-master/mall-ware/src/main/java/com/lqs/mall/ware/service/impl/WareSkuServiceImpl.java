package com.lqs.mall.ware.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.mq.ware.StockDetailTo;
import com.lqs.mall.common.to.mq.ware.WareStockLockTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.ware.dao.WareOrderTaskDao;
import com.lqs.mall.ware.dao.WareOrderTaskDetailDao;
import com.lqs.mall.ware.dao.WareSkuDao;
import com.lqs.mall.ware.entity.WareOrderTaskDetailEntity;
import com.lqs.mall.ware.entity.WareOrderTaskEntity;
import com.lqs.mall.ware.entity.WareSkuEntity;
import com.lqs.mall.ware.exception.NoStockException;
import com.lqs.mall.ware.feign.OrderOpenFeignClientService;
import com.lqs.mall.ware.mq.publisher.WareStockReleasePublisher;
import com.lqs.mall.ware.service.WareSkuService;
import com.lqs.mall.ware.vo.CartCheckSkuHasStockVo;
import com.lqs.mall.ware.vo.OrderItemLockStockVo;
import com.lqs.mall.ware.vo.OrderVo;
import com.lqs.mall.ware.vo.WareSkuLockVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareStockReleasePublisher wareStockReleasePublisher;

    @Autowired
    private WareOrderTaskDao wareOrderTaskDao;

    @Autowired
    private WareOrderTaskDetailDao wareOrderTaskDetailDao;

    @Autowired
    private OrderOpenFeignClientService orderOpenFeignClientService;


    @Override
    @Transactional(readOnly = true)
    // skuId=&wareId=
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new QueryPage<WareSkuEntity>().getPage(params),
                new LambdaQueryWrapper<WareSkuEntity>()
                        .eq(!StringUtils.isEmpty((String) params.get("skuId")), WareSkuEntity::getSkuId, params.get("skuId"))
                        .eq(!StringUtils.isEmpty((String) params.get("wareId")), WareSkuEntity::getWareId, params.get("wareId"))

        );

        return new PageUtils(page);
    }

    // 查询是否sku有库存
    @Transactional(readOnly = true)
    @Override
    public Map<Long, Boolean> getSkuHasStock(List<Long> skuIds) {

        Map<Long, Boolean> skuHasStockToMap = new HashMap<>();

        for (Long skuId : skuIds) {
            Long count = this.baseMapper.getSkuStock(skuId);
            skuHasStockToMap.put(skuId, count > 0 ? true : false);
        }

        return skuHasStockToMap;
    }

    /**
     * 为订单锁库存
     * @param wareSkuLock
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public Boolean orderLockStock(WareSkuLockVo wareSkuLock) {

        // 创建并且保存一个库存工作单
        WareOrderTaskEntity wareOrderTask = new WareOrderTaskEntity();
        wareOrderTask.setOrderSn(wareSkuLock.getOrderSn());
        wareOrderTaskDao.insert(wareOrderTask);

        // 找到每个商品在那些仓库都有库存
        List<OrderItemLockStockVo> wareSkuLocks = wareSkuLock.getLockSkus();
        List<CartCheckSkuHasStockVo> wareSkuHasStockList = wareSkuLocks.stream().map(wareSku -> {
            CartCheckSkuHasStockVo checkSkuHasStockVo = new CartCheckSkuHasStockVo();
            // 查询有库存得仓库
            List<Long> hasStockWareIds = this.baseMapper.selectWareIdHasSkuStock(wareSku.getSkuId());
            checkSkuHasStockVo.setSkuId(wareSku.getSkuId());
            checkSkuHasStockVo.setWareIds(hasStockWareIds);
            checkSkuHasStockVo.setLockNum(wareSku.getCount());
            return checkSkuHasStockVo;
        }).collect(Collectors.toList());


        // 是否全部锁定成功，默认是
        // Boolean allLock = true;
        // 锁定库存
        for (CartCheckSkuHasStockVo cartCheckSkuHasStockVo : wareSkuHasStockList) {
            // 当前商品是否锁定成功
            Boolean skuStocked = false;
            Long skuId = cartCheckSkuHasStockVo.getSkuId();
            List<Long> wareIds = cartCheckSkuHasStockVo.getWareIds();

            if (wareIds == null && wareIds.size() == 0){
                // 没有库存抛出异常
                throw new NoStockException(skuId);
            }

            for (Long wareId : wareIds) {
                // 锁定库存 根据条件一个仓库一个仓库得锁定 只要有一个锁定成功就跳出这个循环 锁定成功返回1 否则0
                Integer lockResultRow = this.baseMapper.lockSkuStock(skuId, wareId, cartCheckSkuHasStockVo.getLockNum());
                if (lockResultRow == 1){
                    skuStocked = true;
                    // 当前商品库存 锁定成功跳出循环 当前操作锁失败 锁下一个仓库
                    // 锁定成功之后 保存库存工作单详情信息 并且 发送消息个mq
                    WareOrderTaskDetailEntity wareOrderTaskDetail = new WareOrderTaskDetailEntity(null, skuId, "", cartCheckSkuHasStockVo.getLockNum(), wareOrderTask.getId(), wareId, Constant.wareStockStatus.LOCK.getCode());
                    wareOrderTaskDetailDao.insert(wareOrderTaskDetail);
                    // 组织mq对象
                    WareStockLockTo wareStockLockTo = new WareStockLockTo();
                    wareStockLockTo.setWareOrderTaskId(wareOrderTask.getId());
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(wareOrderTaskDetail, stockDetailTo);

                    wareStockLockTo.setStockDetail(stockDetailTo);

                    wareStockReleasePublisher.sendWareStockLockTimeDelayMessage(wareStockLockTo);

                    // 发送消息
                    break;
                }

            }
            // 当前商品所有仓库没有一个锁定成功
            if (skuStocked == false){
                throw new NoStockException(skuId);
            }
        }
        // int i = 10/0;
        // 能够走到这里 就说明全部锁定成功了
        return true;
    }

    /**
     * 根据skuId查询商品得库存
     * @param skuId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public CartCheckSkuHasStockVo orderSkuHasStock(Long skuId) {

        List<WareSkuEntity> wareSkus = this.baseMapper.selectList(new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, skuId));

        CartCheckSkuHasStockVo orderSkuHasStock = new CartCheckSkuHasStockVo();

        orderSkuHasStock.setSkuId(skuId);

        Integer totalNum = 0;

        List<Long> wareIds = new ArrayList<>();

        for (WareSkuEntity wareSku : wareSkus) {
            Integer num = wareSku.getStock() - wareSku.getStockLocked();
            totalNum += num;

            wareIds.add(wareSku.getWareId());
        }

        orderSkuHasStock.setSkuStockNum(totalNum);

        orderSkuHasStock.setWareIds(wareIds);

        return orderSkuHasStock;
    }

    /**
     * 解锁库存
     * @param wareStockLockTo
     */
    @Transactional(readOnly = false)
    @Override
    public void releaseWareStock(WareStockLockTo wareStockLockTo) {


        Long wareOrderTaskId = wareStockLockTo.getWareOrderTaskId();
        StockDetailTo stockDetail = wareStockLockTo.getStockDetail();

        // 查询工作单详情是否存在
        WareOrderTaskDetailEntity wareOrderTaskDetail = wareOrderTaskDetailDao.selectById(stockDetail.getId());
        // 如果 不存在这个 库存工作单信息 那就说明之前锁库存得时候出现异常被rollback了
        if (wareOrderTaskDetail != null && wareOrderTaskDetail.getLockStatus() == Constant.wareStockStatus.LOCK.getCode()){
            // 查询订单信息
            // 有这个订单,如果订单被取消了,那么解锁,如果没有被取消,那么不管,如果没有这个订单,一定要解锁,没有订单就说明rollback了
            // 根据库存工作单id查询出整条工作单信息
            WareOrderTaskEntity wareOrderTask = wareOrderTaskDao.selectById(wareOrderTaskId);
            // 从库存工作单中获取订单号
            String orderSn = wareOrderTask.getOrderSn();
            // 远程调用order服务 查询订单
            R orderStatusResponse = orderOpenFeignClientService.requestOrderStatus(orderSn);

            // System.out.println(orderStatusResponse);

            if (orderStatusResponse.parseCode() < 20000){
                OrderVo order = orderStatusResponse.parseType(new TypeReference<OrderVo>() {}, "order");
                // 订单已经被取消了
                if (order == null || order.getStatus() == Constant.orderStatus.CANCEL.getCode()) {
                    // 解锁库存
                    unLockStock(stockDetail.getSkuId(), stockDetail.getWareId(), stockDetail.getSkuNum(), stockDetail.getId());
                }
            }

        }
    }

    /**
     * 根据订单号来解锁库存
     * @param orderSn
     */
    @Transactional(readOnly = false)
    @Override
    public void releaseWareStockByOrderCancelPost(String orderSn) {
        // 查询对应得库存工作单
        WareOrderTaskEntity wareOrderTask = wareOrderTaskDao.selectOne(new LambdaQueryWrapper<WareOrderTaskEntity>().eq(WareOrderTaskEntity::getOrderSn, orderSn));
        // 如果库存工作单存在 那么这里继续执行逻辑 如果工作单不存在 那就说明 锁库存得时候回滚了 不必再解锁
        if (wareOrderTask != null){
            // 获取工作单中得所有工作单详情项
            List<WareOrderTaskDetailEntity> wareOrderTaskDetailList = wareOrderTaskDetailDao.selectList(new LambdaQueryWrapper<WareOrderTaskDetailEntity>().eq(WareOrderTaskDetailEntity::getTaskId, wareOrderTask.getId()));
            // 解锁这些工作单详情中符合status得库存
            wareOrderTaskDetailList.stream()
                    .filter(wareOrderTaskDetail -> wareOrderTaskDetail.getLockStatus() == Constant.wareStockStatus.LOCK.getCode() ? true : false)
                            .forEach(wareOrderTaskDetail -> {
                                unLockStock(wareOrderTaskDetail.getSkuId(), wareOrderTaskDetail.getWareId(), wareOrderTaskDetail.getSkuNum(), wareOrderTaskDetail.getId());
                            });

        }

    }

    /**
     * 订单支付成功之后,扣除库存 并且返回skuIds
     * @param orderSn
     */
    @Transactional(readOnly = false)
    @Override
    public List<Long> deductionWareStock(String orderSn) {

        // 查询到对应订单号得库存工作单
        WareOrderTaskEntity wareOrderTask = wareOrderTaskDao.selectOne(new LambdaQueryWrapper<WareOrderTaskEntity>().eq(WareOrderTaskEntity::getOrderSn, orderSn));

        // 根据库存工作单id查询到 这个工作单里面关联得所有 库存工作单详情项
        List<WareOrderTaskDetailEntity> wareOrderTaskDetailList = wareOrderTaskDetailDao.selectList(new LambdaQueryWrapper<WareOrderTaskDetailEntity>().eq(WareOrderTaskDetailEntity::getTaskId, wareOrderTask.getId()));

        // 筛选出没有被自动解锁和已经扣除得 库存工作单详情项
        List<WareOrderTaskDetailEntity> deductionWareStockWareOrderTaskDetailList = wareOrderTaskDetailList.stream().filter(wareOrderTaskDetail -> wareOrderTaskDetail.getLockStatus() == Constant.wareStockStatus.LOCK.getCode() ? true : false).collect(Collectors.toList());

        // 用于收集skuId
        List<Long> skuIds = new ArrayList<>();

        // 遍历这个符合条件得工作单详情项
        deductionWareStockWareOrderTaskDetailList.stream().forEach(wareOrderTaskDetail -> {

            skuIds.add(wareOrderTaskDetail.getSkuId());

            // 根据 工作单详情项 里面得skuId和wareId找出对应得商品库存信息
            WareSkuEntity wareSku = this.baseMapper.selectOne(new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, wareOrderTaskDetail.getSkuId())
                    .eq(WareSkuEntity::getWareId, wareOrderTaskDetail.getWareId()));

            // 扣除锁定库存
            wareSku.setStockLocked(wareSku.getStockLocked() - wareOrderTaskDetail.getSkuNum());
            // 扣除库存
            wareSku.setStock(wareSku.getStock() - wareOrderTaskDetail.getSkuNum());
            // 保存商品库存信息
            this.baseMapper.updateById(wareSku);
            // 设置工作单详情项状态
            wareOrderTaskDetail.setLockStatus(Constant.wareStockStatus.DEDUCTION.getCode());
            // 保存工作单详情项
            wareOrderTaskDetailDao.updateById(wareOrderTaskDetail);
        });

        return skuIds;

    }

    // 解锁方法
    private void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId) {
        // 解锁符合要求得库存锁定
        this.baseMapper.unLockStock(skuId, wareId, num);

        // 修改库存工作详情单中得状态
        WareOrderTaskDetailEntity orderTaskDetail = new WareOrderTaskDetailEntity();
        orderTaskDetail.setId(taskDetailId);
        orderTaskDetail.setLockStatus(Constant.wareStockStatus.RELEASE.getCode());
        wareOrderTaskDetailDao.updateById(orderTaskDetail);
    }

}