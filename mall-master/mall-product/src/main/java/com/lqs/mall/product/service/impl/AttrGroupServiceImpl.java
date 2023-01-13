package com.lqs.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.AttrAttrgroupRelationDao;
import com.lqs.mall.product.dao.AttrDao;
import com.lqs.mall.product.dao.AttrGroupDao;
import com.lqs.mall.product.dao.CategoryDao;
import com.lqs.mall.product.entity.AttrAttrgroupRelationEntity;
import com.lqs.mall.product.entity.AttrEntity;
import com.lqs.mall.product.entity.AttrGroupEntity;
import com.lqs.mall.product.entity.CategoryEntity;
import com.lqs.mall.product.service.AttrGroupService;
import com.lqs.mall.product.vo.RespAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new QueryPage<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(readOnly = true)
    @Override
    public PageUtils findAttrGroupByCategoryId(Map<String, Object> params, Long categoryId) {

        IPage<AttrGroupEntity> page = this.page(
                new QueryPage<AttrGroupEntity>().getPage(params),
                new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(categoryId != 0L, AttrGroupEntity::getCatelogId, categoryId)
                        .and(params.get("key") != null, query1 -> {
                            query1.like(AttrGroupEntity::getAttrGroupName, params.get("key"))
                                    .or().like(AttrGroupEntity::getDescript, params.get("key"));                        })
        );

        return new PageUtils(page);
    }

    @Transactional(readOnly = true)
    @Override
    public AttrGroupEntity getAttrGroupInfoById(Long attrGroupId) {

        AttrGroupEntity attrGroupEntity = this.baseMapper.selectById(attrGroupId);

        Long catelogId = attrGroupEntity.getCatelogId(); // three

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);

        Long parentCid = categoryEntity.getParentCid(); // two

        CategoryEntity categoryEntity1 = categoryDao.selectById(parentCid);

        Long parentCid1 = categoryEntity1.getParentCid(); // one

        Long[] catelogIds = new Long[3];

        catelogIds[0] = parentCid1;
        catelogIds[1] = parentCid;
        catelogIds[2] = catelogId;

        attrGroupEntity.setCatelogIds(catelogIds);

        return attrGroupEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttrGroupEntity> findAttrGroupByCategoryIdNoLimit(Long categoryId) {

        List<AttrGroupEntity> attrGroupEntityList = this.baseMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>().eq(categoryId >= 0, AttrGroupEntity::getCatelogId, categoryId)
        );


        return attrGroupEntityList;
    }

    @Override
    // 根据属性分组id查询分组关联属性
    @Transactional(readOnly = true)
    public List<AttrEntity> findRelationAttr(Long attrGroupId) {
        List<AttrAttrgroupRelationEntity> attrgroupRelationEntities = attrAttrgroupRelationDao.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq(attrGroupId != 0, AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId));
        // 获取分组对应的属性
        List<AttrEntity> entities = attrgroupRelationEntities.stream().map(attrgoruprelation -> {
            Long attrId = attrgoruprelation.getAttrId();
            AttrEntity attrEntity = attrDao.selectById(attrId);
            return attrEntity;
        }).collect(Collectors.toList());
        return entities;
    }




    @Override
    // 获取属于这个分类的分组和分组里面的属性
    @Transactional(readOnly = true)
    public List<RespAttrGroupVo> findAttrGroupAndBaseAttrByCatId(Long catId) {

        // 查询属于这个分类的所有属性分组
        List<AttrGroupEntity> attrGroupEntities = this.baseMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>().eq(catId != null, AttrGroupEntity::getCatelogId, catId)
        );
        // 查找每个分组的属性进行封装
        List<RespAttrGroupVo> collect = attrGroupEntities.stream().map((item) -> {
            RespAttrGroupVo respAttrGroupVo = new RespAttrGroupVo();
            BeanUtils.copyProperties(item, respAttrGroupVo);
            // 查询分组下的属性
            // 查询分组和属性的对应关系
            List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = attrAttrgroupRelationDao.selectList(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq(item.getAttrGroupId() != null, AttrAttrgroupRelationEntity::getAttrGroupId, item.getAttrGroupId())
            );
            List<AttrEntity> attrs = attrAttrgroupRelationEntities.stream().map(attrItem -> {
                AttrEntity attrEntity = attrDao.selectById(attrItem.getAttrId());
                return attrEntity;
            }).collect(Collectors.toList());
            respAttrGroupVo.setAttrs(attrs);
            return respAttrGroupVo;
        }).collect(Collectors.toList());

        return collect;

    }

    // 删除属性分组,细节删除,被引用的不能删除
    @Transactional(readOnly = false)
    @Override
    public void removeDetailByIds(List<Long> attrGroupIds) {

        for (Long attrGroupId : attrGroupIds) {
            List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = attrAttrgroupRelationDao.selectList(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq(attrGroupId > 0, AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId)
            );

            if (attrAttrgroupRelationEntityList.size() > 0) {
                return ;
            }

            this.baseMapper.deleteById(attrGroupId);
        }


    }
}