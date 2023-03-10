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

    // ????????????sku?????????
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
     * ??????????????????
     * @param wareSkuLock
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public Boolean orderLockStock(WareSkuLockVo wareSkuLock) {

        // ???????????????????????????????????????
        WareOrderTaskEntity wareOrderTask = new WareOrderTaskEntity();
        wareOrderTask.setOrderSn(wareSkuLock.getOrderSn());
        wareOrderTaskDao.insert(wareOrderTask);

        // ?????????????????????????????????????????????
        List<OrderItemLockStockVo> wareSkuLocks = wareSkuLock.getLockSkus();
        List<CartCheckSkuHasStockVo> wareSkuHasStockList = wareSkuLocks.stream().map(wareSku -> {
            CartCheckSkuHasStockVo checkSkuHasStockVo = new CartCheckSkuHasStockVo();
            // ????????????????????????
            List<Long> hasStockWareIds = this.baseMapper.selectWareIdHasSkuStock(wareSku.getSkuId());
            checkSkuHasStockVo.setSkuId(wareSku.getSkuId());
            checkSkuHasStockVo.setWareIds(hasStockWareIds);
            checkSkuHasStockVo.setLockNum(wareSku.getCount());
            return checkSkuHasStockVo;
        }).collect(Collectors.toList());


        // ????????????????????????????????????
        // Boolean allLock = true;
        // ????????????
        for (CartCheckSkuHasStockVo cartCheckSkuHasStockVo : wareSkuHasStockList) {
            // ??????????????????????????????
            Boolean skuStocked = false;
            Long skuId = cartCheckSkuHasStockVo.getSkuId();
            List<Long> wareIds = cartCheckSkuHasStockVo.getWareIds();

            if (wareIds == null && wareIds.size() == 0){
                // ????????????????????????
                throw new NoStockException(skuId);
            }

            for (Long wareId : wareIds) {
                // ???????????? ????????????????????????????????????????????? ???????????????????????????????????????????????? ??????????????????1 ??????0
                Integer lockResultRow = this.baseMapper.lockSkuStock(skuId, wareId, cartCheckSkuHasStockVo.getLockNum());
                if (lockResultRow == 1){
                    skuStocked = true;
                    // ?????????????????? ???????????????????????? ????????????????????? ??????????????????
                    // ?????????????????? ????????????????????????????????? ?????? ???????????????mq
                    WareOrderTaskDetailEntity wareOrderTaskDetail = new WareOrderTaskDetailEntity(null, skuId, "", cartCheckSkuHasStockVo.getLockNum(), wareOrderTask.getId(), wareId, Constant.wareStockStatus.LOCK.getCode());
                    wareOrderTaskDetailDao.insert(wareOrderTaskDetail);
                    // ??????mq??????
                    WareStockLockTo wareStockLockTo = new WareStockLockTo();
                    wareStockLockTo.setWareOrderTaskId(wareOrderTask.getId());
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(wareOrderTaskDetail, stockDetailTo);

                    wareStockLockTo.setStockDetail(stockDetailTo);

                    wareStockReleasePublisher.sendWareStockLockTimeDelayMessage(wareStockLockTo);

                    // ????????????
                    break;
                }

            }
            // ????????????????????????????????????????????????
            if (skuStocked == false){
                throw new NoStockException(skuId);
            }
        }
        // int i = 10/0;
        // ?????????????????? ??????????????????????????????
        return true;
    }

    /**
     * ??????skuId?????????????????????
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
     * ????????????
     * @param wareStockLockTo
     */
    @Transactional(readOnly = false)
    @Override
    public void releaseWareStock(WareStockLockTo wareStockLockTo) {


        Long wareOrderTaskId = wareStockLockTo.getWareOrderTaskId();
        StockDetailTo stockDetail = wareStockLockTo.getStockDetail();

        // ?????????????????????????????????
        WareOrderTaskDetailEntity wareOrderTaskDetail = wareOrderTaskDetailDao.selectById(stockDetail.getId());
        // ?????? ??????????????? ????????????????????? ???????????????????????????????????????????????????rollback???
        if (wareOrderTaskDetail != null && wareOrderTaskDetail.getLockStatus() == Constant.wareStockStatus.LOCK.getCode()){
            // ??????????????????
            // ???????????????,????????????????????????,????????????,?????????????????????,????????????,????????????????????????,???????????????,?????????????????????rollback???
            // ?????????????????????id??????????????????????????????
            WareOrderTaskEntity wareOrderTask = wareOrderTaskDao.selectById(wareOrderTaskId);
            // ????????????????????????????????????
            String orderSn = wareOrderTask.getOrderSn();
            // ????????????order?????? ????????????
            R orderStatusResponse = orderOpenFeignClientService.requestOrderStatus(orderSn);

            // System.out.println(orderStatusResponse);

            if (orderStatusResponse.parseCode() < 20000){
                OrderVo order = orderStatusResponse.parseType(new TypeReference<OrderVo>() {}, "order");
                // ????????????????????????
                if (order == null || order.getStatus() == Constant.orderStatus.CANCEL.getCode()) {
                    // ????????????
                    unLockStock(stockDetail.getSkuId(), stockDetail.getWareId(), stockDetail.getSkuNum(), stockDetail.getId());
                }
            }

        }
    }

    /**
     * ??????????????????????????????
     * @param orderSn
     */
    @Transactional(readOnly = false)
    @Override
    public void releaseWareStockByOrderCancelPost(String orderSn) {
        // ??????????????????????????????
        WareOrderTaskEntity wareOrderTask = wareOrderTaskDao.selectOne(new LambdaQueryWrapper<WareOrderTaskEntity>().eq(WareOrderTaskEntity::getOrderSn, orderSn));
        // ??????????????????????????? ?????????????????????????????? ???????????????????????? ???????????? ??????????????????????????? ???????????????
        if (wareOrderTask != null){
            // ?????????????????????????????????????????????
            List<WareOrderTaskDetailEntity> wareOrderTaskDetailList = wareOrderTaskDetailDao.selectList(new LambdaQueryWrapper<WareOrderTaskDetailEntity>().eq(WareOrderTaskDetailEntity::getTaskId, wareOrderTask.getId()));
            // ????????????????????????????????????status?????????
            wareOrderTaskDetailList.stream()
                    .filter(wareOrderTaskDetail -> wareOrderTaskDetail.getLockStatus() == Constant.wareStockStatus.LOCK.getCode() ? true : false)
                            .forEach(wareOrderTaskDetail -> {
                                unLockStock(wareOrderTaskDetail.getSkuId(), wareOrderTaskDetail.getWareId(), wareOrderTaskDetail.getSkuNum(), wareOrderTaskDetail.getId());
                            });

        }

    }

    /**
     * ????????????????????????,???????????? ????????????skuIds
     * @param orderSn
     */
    @Transactional(readOnly = false)
    @Override
    public List<Long> deductionWareStock(String orderSn) {

        // ??????????????????????????????????????????
        WareOrderTaskEntity wareOrderTask = wareOrderTaskDao.selectOne(new LambdaQueryWrapper<WareOrderTaskEntity>().eq(WareOrderTaskEntity::getOrderSn, orderSn));

        // ?????????????????????id????????? ???????????????????????????????????? ????????????????????????
        List<WareOrderTaskDetailEntity> wareOrderTaskDetailList = wareOrderTaskDetailDao.selectList(new LambdaQueryWrapper<WareOrderTaskDetailEntity>().eq(WareOrderTaskDetailEntity::getTaskId, wareOrderTask.getId()));

        // ???????????????????????????????????????????????? ????????????????????????
        List<WareOrderTaskDetailEntity> deductionWareStockWareOrderTaskDetailList = wareOrderTaskDetailList.stream().filter(wareOrderTaskDetail -> wareOrderTaskDetail.getLockStatus() == Constant.wareStockStatus.LOCK.getCode() ? true : false).collect(Collectors.toList());

        // ????????????skuId
        List<Long> skuIds = new ArrayList<>();

        // ?????????????????????????????????????????????
        deductionWareStockWareOrderTaskDetailList.stream().forEach(wareOrderTaskDetail -> {

            skuIds.add(wareOrderTaskDetail.getSkuId());

            // ?????? ?????????????????? ?????????skuId???wareId?????????????????????????????????
            WareSkuEntity wareSku = this.baseMapper.selectOne(new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, wareOrderTaskDetail.getSkuId())
                    .eq(WareSkuEntity::getWareId, wareOrderTaskDetail.getWareId()));

            // ??????????????????
            wareSku.setStockLocked(wareSku.getStockLocked() - wareOrderTaskDetail.getSkuNum());
            // ????????????
            wareSku.setStock(wareSku.getStock() - wareOrderTaskDetail.getSkuNum());
            // ????????????????????????
            this.baseMapper.updateById(wareSku);
            // ??????????????????????????????
            wareOrderTaskDetail.setLockStatus(Constant.wareStockStatus.DEDUCTION.getCode());
            // ????????????????????????
            wareOrderTaskDetailDao.updateById(wareOrderTaskDetail);
        });

        return skuIds;

    }

    // ????????????
    private void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId) {
        // ?????????????????????????????????
        this.baseMapper.unLockStock(skuId, wareId, num);

        // ???????????????????????????????????????
        WareOrderTaskDetailEntity orderTaskDetail = new WareOrderTaskDetailEntity();
        orderTaskDetail.setId(taskDetailId);
        orderTaskDetail.setLockStatus(Constant.wareStockStatus.RELEASE.getCode());
        wareOrderTaskDetailDao.updateById(orderTaskDetail);
    }

}