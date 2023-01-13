package com.lqs.mall.product.dao;

import com.lqs.mall.product.entity.ProductAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu属性值
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {

    ProductAttrValueEntity getSpuAttrValueByAttrId(@Param("attrId") Long attrId, @Param("spuId") Long spuId);
}
