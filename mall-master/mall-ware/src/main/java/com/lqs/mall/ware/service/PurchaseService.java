package com.lqs.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.ware.entity.PurchaseEntity;
import com.lqs.mall.ware.vo.MergeVo;
import com.lqs.mall.ware.vo.PurchaseFinishVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 22:00:14
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<PurchaseEntity> queryUnreceive(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void receivePurchase(List<Long> purchaseIds);

    void finishPurchase(PurchaseFinishVo purchaseFinishVo);
}

