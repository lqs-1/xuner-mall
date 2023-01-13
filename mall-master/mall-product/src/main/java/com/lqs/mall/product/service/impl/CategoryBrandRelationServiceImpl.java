package com.lqs.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.BrandDao;
import com.lqs.mall.product.dao.CategoryBrandRelationDao;
import com.lqs.mall.product.dao.CategoryDao;
import com.lqs.mall.product.entity.BrandEntity;
import com.lqs.mall.product.entity.CategoryBrandRelationEntity;
import com.lqs.mall.product.entity.CategoryEntity;
import com.lqs.mall.product.service.CategoryBrandRelationService;
import com.lqs.mall.product.vo.CategoryBrandRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {


    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;


    @Transactional(readOnly = true)
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new QueryPage<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据品牌id获取分组和品牌关系的列表
     * @param brandId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<CategoryBrandRelationEntity> getCategoryAndBrandRelationByBrandId(Long brandId) {

        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = this.baseMapper.selectList(
                new LambdaQueryWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getBrandId, brandId)
        );

        return categoryBrandRelationEntityList;

    }


    /**
     * 保存，细节添加分类和品牌的关联关系
     * @param categoryBrandRelationVo
     */
    @Transactional(readOnly = false)
    @Override
    public R saveDetail(CategoryBrandRelationVo categoryBrandRelationVo) {

        CategoryBrandRelationEntity existEntity = this.baseMapper.selectOne(
                new LambdaQueryWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getBrandId, categoryBrandRelationVo.getBrandId())
                        .and(query2 -> {
                            query2.eq(CategoryBrandRelationEntity::getCatelogId, categoryBrandRelationVo.getCatelogId());
                        })
        );

        if (existEntity != null){
            return R.error(REnum.APPEND_CATEGORY_AND_BRAND_BINDING_RELATION_EXIST.getStatusCode(),
                    REnum.APPEND_CATEGORY_AND_BRAND_BINDING_RELATION_EXIST.getStatusMsg());
        }


        // 查出分类名和品牌名
        CategoryEntity categoryEntity = categoryDao.selectById(categoryBrandRelationVo.getCatelogId());

        BrandEntity brandEntity = brandDao.selectById(categoryBrandRelationVo.getBrandId());

        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();

        BeanUtils.copyProperties(categoryBrandRelationVo, categoryBrandRelationEntity);

        categoryBrandRelationEntity.setBrandName(brandEntity.getName());

        categoryBrandRelationEntity.setCatelogName(categoryEntity.getName());

        // 保持

        this.save(categoryBrandRelationEntity);

        return R.ok(REnum.APPEND_CATEGORY_AND_BRAND_BINDING_RELATION_SUCCESS.getStatusCode(),
                REnum.APPEND_CATEGORY_AND_BRAND_BINDING_RELATION_SUCCESS.getStatusMsg());

    }


    /**
     * 根据catid获取对应的品牌,获取对应分类下的品牌
     * @param catId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<BrandEntity> getBrandsByCatid(Long catId) {

        if (catId == 0){
            return null;
        }

        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = this.baseMapper.selectList(
                new LambdaQueryWrapper<CategoryBrandRelationEntity>()
                        .eq(catId != 0, CategoryBrandRelationEntity::getCatelogId, catId)
        );

        List<Long> brandIdList = categoryBrandRelationEntityList.stream().map(item ->
            item.getBrandId()
        ).collect(Collectors.toList());


        List<BrandEntity> brandEntityList = brandIdList.stream().map(item -> {
            BrandEntity brandEntity = brandDao.selectById(item);
            return brandEntity;
        }).collect(Collectors.toList());

        return brandEntityList;
    }

}