package com.lqs.mall.cart.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName CartItemVo
 * @date 2022/10/24 下午1:01
 * @do 购物项内容
 */

@ToString
public class CartItemVo {

    @Getter
    @Setter
    private Long skuId; // 商品skuId

    @Getter
    @Setter
    private Boolean check = true; // 该购物车中的对应商品是否选中

    @Getter
    @Setter
    private String title; // 对应商品的标题

    @Getter
    @Setter
    private String image; // 对应商品的默认图片

    @Getter
    @Setter
    private List<String> skuAttr; // 对应商品的属性是销售属性

    @Getter
    @Setter
    private BigDecimal price; // 对应商品的价格

    @Getter
    @Setter
    private Integer count; // 对应商品的个数

    @Getter
    @Setter
    private Boolean hasStock; // 是否有库存


    public BigDecimal getTotalPrice() {

        return price.multiply(new BigDecimal(count));
    }
}
