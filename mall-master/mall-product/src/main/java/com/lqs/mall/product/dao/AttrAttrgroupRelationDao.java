package com.lqs.mall.product.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lqs.mall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    default void deleteByAttrIds(List<Long> attrIds) {
        delete(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().in(AttrAttrgroupRelationEntity::getAttrId, attrIds));
    }

    List<AttrAttrgroupRelationEntity> getRelationByAttrGroupId(@Param("attrGroupId") Long attrGroupId);
}
