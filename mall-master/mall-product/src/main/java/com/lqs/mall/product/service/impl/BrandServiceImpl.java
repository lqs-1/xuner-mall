package com.lqs.mall.product.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.BrandDao;
import com.lqs.mall.product.dao.CategoryBrandRelationDao;
import com.lqs.mall.product.entity.BrandEntity;
import com.lqs.mall.product.entity.CategoryBrandRelationEntity;
import com.lqs.mall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {


    @Autowired
    private CategoryBrandRelationDao categoryBrandRelationDao;

    @Transactional(readOnly = true)
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<BrandEntity> brand = new LambdaQueryWrapper<>();
        brand.eq(true, BrandEntity::getDescript, params.get("key"));



        IPage<BrandEntity> page = this.page(
                new QueryPage<BrandEntity>().getPage(params, false),
                new LambdaQueryWrapper<BrandEntity>()
                        // 以下三个的写法都可以
                        // 第一个条件
                        .like(true, // 这个参数表示是否这个条件生效,可以写成表达式
                                BrandEntity::getName, // 要查询的对应数据库的pojo属性名
                                (String)params.get("key")) // 条件值
                        // 第二个条件
                        .or()
                        .like(true,
                                BrandEntity::getDescript,
                                (String)params.get("key"))
                        // 第三个条件
                        .or(true, wrapper -> {
                            wrapper.like(true,
                                            BrandEntity::getFirstLetter,
                                            (String)params.get("key"));
                        })
        );

        return new PageUtils(page);
    }

    /**
     * 更新细节,被引用了级联修改
     * @param brand
     */
    @Transactional(readOnly = false)
    @Override
    public void updateDetailById(BrandEntity brand) {


        this.baseMapper.updateById(brand);

        if (!StringUtils.isEmpty(brand.getName())){
            CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
            categoryBrandRelationEntity.setBrandId(brand.getBrandId());
            categoryBrandRelationEntity.setBrandName(brand.getName());
            // 更新关联表
            LambdaUpdateWrapper<CategoryBrandRelationEntity> categoryBrandRelationEntityUpdateWrapper = new LambdaUpdateWrapper<>();
            categoryBrandRelationEntityUpdateWrapper.eq(true, CategoryBrandRelationEntity::getBrandId, brand.getBrandId());
            categoryBrandRelationDao.update(categoryBrandRelationEntity, categoryBrandRelationEntityUpdateWrapper);
            // TODO 后续继续更新
        }
    }


    /**
     * 删除,被引用不允许删除
     * @param brandIdList
     */
    @Transactional(readOnly = false)
    @Override
    public void removeDetailByIds(List<Long> brandIdList) {

        // 查看是否被引用 TODO 后期再说
        for (Long brandId : brandIdList) {
            List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = categoryBrandRelationDao.selectList(
                    new LambdaQueryWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getBrandId, brandId)
            );

            if (categoryBrandRelationEntityList.size() == 0){
                this.removeById(brandId);
            }

        }

    }

    // 根据品牌id查询品牌名
    @Override
    public String findBrandNameByBrandId(Long brandId) {

        BrandEntity brandEntity = this.baseMapper.selectById(brandId);

        return brandEntity.getName();
    }

}