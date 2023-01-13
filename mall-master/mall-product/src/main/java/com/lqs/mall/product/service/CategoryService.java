package com.lqs.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.CategoryEntity;
import com.lqs.mall.product.vo.Catelog2WebVo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> cateIds);

    void updateDetailById(CategoryEntity category);

    Long[] findCatelogPath(Long catelogId);

    List<CategoryEntity> findCatelogLevelOneList();

    Map<String, List<Catelog2WebVo>> getCatalogJson();

}


