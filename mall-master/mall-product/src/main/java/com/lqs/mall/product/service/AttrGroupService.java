package com.lqs.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.AttrEntity;
import com.lqs.mall.product.entity.AttrGroupEntity;
import com.lqs.mall.product.vo.RespAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils findAttrGroupByCategoryId(Map<String, Object> params, Long categoryId);

    AttrGroupEntity getAttrGroupInfoById(Long attrGroupId);

    List<AttrGroupEntity> findAttrGroupByCategoryIdNoLimit(Long categoryId);

    List<AttrEntity> findRelationAttr(Long attrGroupId);

    List<RespAttrGroupVo> findAttrGroupAndBaseAttrByCatId(Long catId);

    void removeDetailByIds(List<Long> attrGroupIds);
}

