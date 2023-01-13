package com.lqs.mall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqs.mall.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}
