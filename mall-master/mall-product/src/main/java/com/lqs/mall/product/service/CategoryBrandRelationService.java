package com.lqs.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.BrandEntity;
import com.lqs.mall.product.entity.CategoryBrandRelationEntity;
import com.lqs.mall.product.vo.CategoryBrandRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryBrandRelationEntity> getCategoryAndBrandRelationByBrandId(Long brandId);

    R saveDetail(CategoryBrandRelationVo categoryBrandRelationVo);

    List<BrandEntity> getBrandsByCatid(Long catId);
}

