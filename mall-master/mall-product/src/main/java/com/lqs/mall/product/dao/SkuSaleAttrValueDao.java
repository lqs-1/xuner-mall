package com.lqs.mall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqs.mall.product.entity.SkuSaleAttrValueEntity;
import com.lqs.mall.product.vo.item.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {



    List<SkuItemSaleAttrVo> selectSaleAttrsBySkuIds(@Param("skuIds") List<Long> skuIds);

    List<String> getSkuSaleAttrValuesAsList(@Param("skuId") Long skuId);
}
