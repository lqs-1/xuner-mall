package com.lqs.mall.product.vo.item;

import com.lqs.mall.product.entity.SkuImagesEntity;
import com.lqs.mall.product.entity.SkuInfoEntity;
import com.lqs.mall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName SkuItemVo
 * @date 2022/10/16 下午1:35
 * @do 页面要的商品详情信息封装对象
 */

@Data
public class SkuItemVo {
    // sku基本信息
    private SkuInfoEntity skuInfo;

    // 是否有库存
    private boolean hasStock = true;

    // sku图片信息
    private List<SkuImagesEntity> skuImages;

    // spu的销售属性
    private List<SkuItemSaleAttrVo> skuItemSaleAttrs;

    // spu的基本信息，介绍
    private SpuInfoDescEntity spuInfoDesc;

    // spu的规格参数信息
    private List<SpuItemBaseAttrVo> groupAttrs;

    

    @Data
    public static class SpuItemBaseAttrVo{
        private String groupName;
        private List<SpuBaseAttrVo> attrs;
    }

    @Data
    public static class SpuBaseAttrVo{
        private String attrName;
        private String attrValue;
    }




























}
