package com.lqs.mall.order.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName OrderConfirmVo
 * @date 2022/10/28 下午1:57
 * @do 订单确认页需要用的数据
 */

@ToString
public class OrderConfirmVo {

    // 收获地址 mall_ums
    @Getter
    @Setter
    private List<MemberAddrVo> addrList;

    // 选中的所有购物项
    @Getter
    @Setter
    private List<OrderItemVo> orderItemList;


    // TODO 暂时不做 发票信息

    // 积分信息
    @Getter
    @Setter
    private Integer integration;

    // 防止重复下单令牌 解决幂等性问题
    @Getter
    @Setter
    private String orderToken;

    // 订单是否全部有货
    @Getter
    @Setter
    private Boolean hasStock;


    // 订单总额
     // private BigDecimal total;

    // 应付价格
     // private BigDecimal payPrice;

     // 邮费
    private BigDecimal freightPrice;

    // 优惠
    private BigDecimal disCountPrice;


    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
    }

    public BigDecimal getDisCountPrice() {
        return disCountPrice;
    }

    public void setDisCountPrice(BigDecimal disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public BigDecimal getTotal() {
        BigDecimal totalPrice = new BigDecimal("0.0");
        if (orderItemList != null){
            for (OrderItemVo orderItemVo : orderItemList) {
                BigDecimal itemTotal = orderItemVo.getPrice().multiply(new BigDecimal(orderItemVo.getCount()));
                totalPrice = totalPrice.add(itemTotal);
            }
        }
        return totalPrice;
    }

    public BigDecimal getPayPrice() {
        return getTotal().add(getFreightPrice()).subtract(getDisCountPrice());
    }


    public Integer getTotalCount() {
        Integer sumCount = 0;
        if (orderItemList != null){
            for (OrderItemVo orderItemVo : orderItemList) {
                sumCount += orderItemVo.getCount();
            }
        }
        return sumCount;
    }
}
