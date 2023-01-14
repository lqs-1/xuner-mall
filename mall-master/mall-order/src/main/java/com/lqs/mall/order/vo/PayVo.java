package com.lqs.mall.order.vo;

import lombok.Data;

@Data
public class PayVo {
    private String out_trade_no; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String total_amount;  // 付款金额 必填
    private String orderDesc; // 商品描述 可空
    /**
     * 收单:
     * 为了防止用户在订单支付页面一直不支付 导致订单被系统自动取消 库存被系统自动解锁 设置了这个之后 订单页面停留到了这个时间支付直接失败
     */
    private String timeout_express; // 订单绝对超时时间。格式为yyyy-MM-dd HH:mm:ss。超时时间范围：1m~15d
}
