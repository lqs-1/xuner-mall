package com.lqs.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.to.mq.ware.WareStockLockTo;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.ware.entity.WareSkuEntity;
import com.lqs.mall.ware.vo.CartCheckSkuHasStockVo;
import com.lqs.mall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 22:00:14
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map<Long, Boolean> getSkuHasStock(List<Long> skuIds);

    Boolean orderLockStock(WareSkuLockVo wareSkuLock);

    CartCheckSkuHasStockVo orderSkuHasStock(Long skuId);

    void releaseWareStock(WareStockLockTo wareStockLockTo);

    void releaseWareStockByOrderCancelPost(String orderSn);

    List<Long> deductionWareStock(String orderSn);
}

