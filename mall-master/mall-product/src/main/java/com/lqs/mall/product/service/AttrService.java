package com.lqs.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.AttrEntity;
import com.lqs.mall.product.vo.AttrVo;
import com.lqs.mall.product.vo.RespAttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attrVo);

    PageUtils findAttrList(Map<String, Object> params, String type, Long catId);

    RespAttrVo getAtrById(Long attrId);

    void updateAttr(AttrVo attr);

    void removeAttrByIds(List<Long> attrIds);

    PageUtils findNoRelationAttr(Long attrGroupId, Map<String, Object> params);

    List<AttrEntity> findSaleAttrListByCatId(Long catId);
}

