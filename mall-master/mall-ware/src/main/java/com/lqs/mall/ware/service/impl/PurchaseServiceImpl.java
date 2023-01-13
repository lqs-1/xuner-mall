package com.lqs.mall.ware.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.ware.dao.PurchaseDao;
import com.lqs.mall.ware.dao.PurchaseDetailDao;
import com.lqs.mall.ware.dao.WareSkuDao;
import com.lqs.mall.ware.entity.PurchaseDetailEntity;
import com.lqs.mall.ware.entity.PurchaseEntity;
import com.lqs.mall.ware.entity.WareSkuEntity;
import com.lqs.mall.ware.feign.ProductFeignClientService;
import com.lqs.mall.ware.service.PurchaseService;
import com.lqs.mall.ware.vo.MergeVo;
import com.lqs.mall.ware.vo.PurchaseFinishVo;
import com.lqs.mall.ware.vo.PurchaseItemFinishVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailDao purchaseDetailDao;

    @Autowired
    private WareSkuDao wareSkuDao;

    @Autowired
    private ProductFeignClientService productFeignClientService;

    @Override
    // key=&status=
    @Transactional(readOnly = true)
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new QueryPage<PurchaseEntity>().getPage(params),
                new LambdaQueryWrapper<PurchaseEntity>()
                        .eq(!StringUtils.isEmpty((String) params.get("status")), PurchaseEntity::getStatus, params.get("status"))
                        .eq(!StringUtils.isEmpty((String) params.get("key")), PurchaseEntity::getId, params.get("key"))

        );

        return new PageUtils(page);
    }

    @Override
    // 获取符合要求的采购单
    @Transactional(readOnly = true)
    public  List<PurchaseEntity> queryUnreceive(Map<String, Object> params) {
        List<Integer> purchseStatus = new ArrayList<>();
        purchseStatus.add(Constant.purchaseStatus.CREATE.getCode());
        purchseStatus.add(Constant.purchaseStatus.ASSIGNED.getCode());


        List<PurchaseEntity> purchaseEntityList = this.baseMapper.selectList(new LambdaQueryWrapper<PurchaseEntity>().in(true, PurchaseEntity::getStatus, purchseStatus));

        return purchaseEntityList;
    }


    // 查询符合合并要求的采购需求
    private List<Long> queryPurchaseNeedIdList(List<Long> purchaseDetailIds){
        // 获取所有的采购项
        List<PurchaseDetailEntity> purchaseNeedList = purchaseDetailDao.selectBatchIds(purchaseDetailIds);
        // 筛选出所有采购项的状态是0的
        List<Long> purchaseNeedIds = purchaseNeedList.stream().filter(item -> {
            if (item.getStatus() == Constant.purchaseDetail.NEW.getCode()) {
                return true;
            }
            return false;
        }).map(item -> item.getId()).collect(Collectors.toList());

        return purchaseNeedIds;
    }


    @Override
    // 合并采购需求到采购单
    @Transactional(readOnly = false)
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            List<Long> purchaseNeedIds = queryPurchaseNeedIdList(mergeVo.getItems());
            // 如果符合要求的个数和前端传递的个数相同，那么就创建一个新的采购单
            if (purchaseNeedIds.size() == mergeVo.getItems().size()){
                PurchaseEntity purchaseEntity = new PurchaseEntity();
                purchaseEntity.setCreateTime(new Date());
                purchaseEntity.setUpdateTime(new Date());
                purchaseEntity.setStatus(Constant.purchaseStatus.CREATE.getCode());

                this.baseMapper.insert(purchaseEntity);

                purchaseId = purchaseEntity.getId();
            }
        }

        // 如果给了采购单的id那么只能是采购单的状态是0,和1才可以合并（新建和已分配），且如果是本身采购需求有已经分配了的，那么也不行，只能是新建的
        PurchaseEntity purchase = this.getById(purchaseId);
        // 判断采购单的状态是否为0和1
        if (purchase.getStatus() == Constant.purchaseStatus.ASSIGNED.getCode() || purchase.getStatus() == Constant.purchaseStatus.CREATE.getCode()){

            List<Long> purchaseNeedIds = queryPurchaseNeedIdList(mergeVo.getItems());

            // 开始合并
            Long finalPurchaseId = purchaseId;

            List<PurchaseDetailEntity> purchaseDetailEntityList = purchaseNeedIds.stream().map(purchaseNeedId -> {
                PurchaseDetailEntity purchaseNeed = new PurchaseDetailEntity();
                purchaseNeed.setId(purchaseNeedId);
                purchaseNeed.setPurchaseId(finalPurchaseId);
                purchaseNeed.setStatus(Constant.purchaseDetail.RECIVE.getCode());
                return purchaseNeed;
            }).collect(Collectors.toList());

            purchaseDetailEntityList.stream().forEach(purchaseDetailDao::updateById);

            // 更新时间
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setId(purchaseId);
            purchaseEntity.setUpdateTime(new Date());
            this.baseMapper.updateById(purchaseEntity);

        }

    }

    /**
     * 领取采购单（可能是多个）
     * @param purchaseIds
     */
    @Transactional(readOnly = false)
    @Override
    public void receivePurchase(List<Long> purchaseIds) {
        // 确认当前采购单是新建或者已分配状态
        List<PurchaseEntity> purchaseEntityList = purchaseIds.stream().map(id -> {
            // 先查出对应id的采购单
            PurchaseEntity purchase = this.getById(id);
            return purchase;
        }).filter(item -> {
            // 筛选需要改变状态的采购单
            if (item.getStatus() == Constant.purchaseStatus.ASSIGNED.getCode()){
                return true;
            }
            return false;
        }).map(item -> {
            // 给需要修改状态的采购单进行设置
            item.setStatus(Constant.purchaseStatus.RECIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        // 改变采购单的状态
        this.updateBatchById(purchaseEntityList);

        // 改变采购项目的状态
        purchaseEntityList.forEach(item -> {
            // 查出这个采购单下面的所有采购项
            List<PurchaseDetailEntity> purchaseNeedList = purchaseDetailDao.selectListPurchaseNeedByPurchaseId(item.getId());
            // 填充采购项的最新状态
            List<PurchaseDetailEntity> purchaseDetailEntityList = purchaseNeedList.stream().map(purchaseNeed -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(purchaseNeed.getId());
                purchaseDetailEntity.setStatus(Constant.purchaseDetail.RECIVEING.getCode());

                return purchaseDetailEntity;
            }).collect(Collectors.toList());

            purchaseDetailEntityList.stream().forEach(purchaseDetailDao::updateById);
        });


    }

    // 采购完成
    @Transactional(readOnly = false)
    @Override
    public void finishPurchase(PurchaseFinishVo purchaseFinishVo) {

        Long purchaseId = purchaseFinishVo.getId();

        // 改变采购项的状态
        Boolean flag = true; // 记录采购单的状态是否没有异常
        // 获取vo中的items列表
        List<PurchaseItemFinishVo> items = purchaseFinishVo.getItems();

        // 创建一个采购项批量更新的列表
        List<PurchaseDetailEntity> updates = new ArrayList<>();
        // 遍历查看是否存在采购失败的
        for (PurchaseItemFinishVo item : items) {
            // 采购项的状态必须要是2(正在采购)才执行下面的所有操作
            PurchaseDetailEntity entity = purchaseDetailDao.selectById(item.getItemId());
            if (entity.getStatus() == Constant.purchaseDetail.RECIVEING.getCode()){

                PurchaseDetailEntity purchaseNeedEntity = new PurchaseDetailEntity();
                if (item.getStatus() == Constant.purchaseDetail.FAIL.getCode()){
                    // 如果有采购失败的采购项，那么就将标记设置为false
                    flag = false;
                }else {
                    // 将成功采购的进行入库
                    // 查询是否存在这个商品的库存信息
                    List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new LambdaQueryWrapper<WareSkuEntity>()
                            .eq(true, WareSkuEntity::getSkuId, entity.getSkuId())
                            .eq(true, WareSkuEntity::getWareId, entity.getWareId()));

                    if (wareSkuEntities == null || wareSkuEntities.size() == 0){
                        WareSkuEntity wareSkuEntity = new WareSkuEntity();
                        wareSkuEntity.setSkuId(entity.getSkuId());
                        wareSkuEntity.setStock(entity.getSkuNum());
                        wareSkuEntity.setWareId(entity.getWareId());
                        wareSkuEntity.setStockLocked(0);

                        String skuName = "";
                        try{
                            // 程序代码
                            R skuInfo = productFeignClientService.skuName(entity.getSkuId());
                            skuName = (String) skuInfo.get("skuName");
                        }catch(Exception e){
                            e.printStackTrace();
                            // 程序代码
                            skuName = "";
                        }finally{
                            // 程序代码

                        }
                        wareSkuEntity.setSkuName(skuName);
                        wareSkuDao.insert(wareSkuEntity);
                    }else {
                        wareSkuDao.updateStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
                    }
                }
                purchaseNeedEntity.setId(item.getItemId());
                purchaseNeedEntity.setStatus(item.getStatus());
                updates.add(purchaseNeedEntity);
            }
            // 批量更新
            updates.stream().forEach(purchaseDetailDao::updateById);
            // 创建采购单的对象
            // 改变采购单的状态
            PurchaseEntity purchase = new PurchaseEntity();
            purchase.setId(purchaseId);
            purchase.setUpdateTime(new Date());
            purchase.setStatus(flag ? Constant.purchaseStatus.FINISH.getCode() : Constant.purchaseStatus.HASERROR.getCode());
            this.updateById(purchase);
            }


    }

}