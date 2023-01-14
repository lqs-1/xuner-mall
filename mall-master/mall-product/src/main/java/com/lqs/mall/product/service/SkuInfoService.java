package com.lqs.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.to.SaleNumTo;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.SkuInfoEntity;
import com.lqs.mall.product.vo.item.SkuItemVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SkuItemVo item(Long skuId);

    BigDecimal getLatestPrice(Long skuId);

    void updateSkuSaleNum(List<SaleNumTo> saleNumList);
}

