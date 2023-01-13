package com.lqs.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.AttrAttrgroupRelationDao;
import com.lqs.mall.product.entity.AttrAttrgroupRelationEntity;
import com.lqs.mall.product.service.AttrAttrgroupRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements  AttrAttrgroupRelationService{

    @Override
    @Transactional(readOnly = true)
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new QueryPage<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateAttrAttrGroupRelation(List<Map<String, Long>> params) {
        Long attrGroupId = null;
        List<Long> attrIds = new ArrayList<>();
        for (Map<String, Long> param : params) {
            attrGroupId = (Long) param.get("attrGroupId");
            attrIds.add((Long) param.get("attrId"));
        }
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);
        this.update(
                attrAttrgroupRelationEntity,
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().in(attrIds.size() > 0, AttrAttrgroupRelationEntity::getAttrId, attrIds)
        );
    }

    @Override
    @Transactional(readOnly = false)
    public void removeAttrAttrGroupRelation(List<Map<String, Long>> params) {
        Long attrGroupId = null;
        List<Long> attrIds = new ArrayList<>();
        for (Map<String, Long> param : params) {
            attrGroupId = (Long) param.get("attrGroupId");
            attrIds.add((Long) param.get("attrId"));
        }

        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(-1L);
        this.update(
                attrAttrgroupRelationEntity,
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .in(attrIds.size() > 0, AttrAttrgroupRelationEntity::getAttrId, attrIds)
                        .eq(attrGroupId != null, AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId)
        );
    }

}