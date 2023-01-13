package com.lqs.mall.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName SkuEsModelTo
 * @date 2022/9/28 下午4:34
 * @do 存储到es里面的商品信息传递模型
 */

@Data
public class SkuEsModelTo {

    private Long spuId;

    private Long skuId;

    private String skuTitle;

    private BigDecimal skuPrice;

    private String skuImg;

    private Long saleCount;

    private Boolean hasStock;

    private Long hotScore;

    private Long brandId;

    private Long catalogId;

    private String brandName;

    private String brandImg;

    private String catalogName;

    private List<Attr> attrs;

    @Data
    public static class Attr{

        private Long attrId;

        private String attrName;

        private String attrValue;

    }

}
