package com.lqs.mall.product.vo.item;

import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName SkuItemSaleAttrVo
 * @date 2022/10/16 下午3:11
 * @do spu的销售属性
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
