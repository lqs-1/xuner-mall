package com.lqs.mall.order.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName OrderItemVo
 * @date 2022/10/28 下午2:07
 * @do 订单确认页需要用的选中的购物项数据
 */
public class OrderItemVo {

    // 是否有库存
    private Boolean hasStock = true; // 是否有货

    private Long skuId; // 商品skuId

    private Boolean check = true; // 该购物车中的对应商品是否选中

    private String title; // 对应商品的标题

    private String image; // 对应商品的默认图片

    private List<String> skuAttr; // 对应商品的属性是销售属性

    private BigDecimal price; // 对应商品的价格

    private Integer count; // 对应商品的个数

    private BigDecimal totalPrice; // 这个商品的总价，price*count

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(Boolean hasStock) {
        this.hasStock = hasStock;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSkuAttr() {
        return skuAttr;
    }

    public void setSkuAttr(List<String> skuAttr) {
        this.skuAttr = skuAttr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 计算当前商品总价  multiply表示乘
     * @return
     */
    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal(this.count.toString()));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }




}
