package com.lqs.mall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName CartVo
 * @date 2022/10/24 下午1:01
 * @do 整个购物车
 */
public class CartVo {

    private List<CartItemVo> items; // 各个购物项

    private Integer countNum; // 所有商品的数量

    private Integer countType; // 商品类型数量

    private BigDecimal totalAmount; // 商品总价

    private BigDecimal reduce = new BigDecimal(0); // 减免价格


    public List<CartItemVo> getItems() {
        return items;
    }

    public void setItems(List<CartItemVo> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (items != null){
            for (CartItemVo item : items) {
                count += 1;
            }
        }
        return count;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public Integer getCountType() {
        int count = 0;
        if (items != null){
            for (CartItemVo item : items) {
                count += item.getCount();
            }
        }
        return countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        // 计算购物项总价
        if (items != null){
            for (CartItemVo item : items) {
                if (item.getCheck()){
                    BigDecimal totalPrice = item.getTotalPrice();
                    amount = amount.add(totalPrice);
                }
            }
        }
        // 减免
        BigDecimal realTotalPrice = amount.subtract(getReduce());
        return realTotalPrice;
    }

    // public void setTotalAmount(BigDecimal totalAmount) {
    //     this.totalAmount = totalAmount;
    // }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
