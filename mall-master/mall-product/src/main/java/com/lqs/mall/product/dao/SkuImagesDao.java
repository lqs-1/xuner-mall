package com.lqs.mall.product.dao;

import com.lqs.mall.product.entity.SkuImagesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku图片
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@Mapper
public interface SkuImagesDao extends BaseMapper<SkuImagesEntity> {

    List<SkuImagesEntity> getSkuImagesBySkuId(@Param("skuId") Long skuId);
}
