package com.lqs.mall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李奇凇
 * @moduleName OrderSubmitVo
 * @date 2022/10/28 下午7:53
 * @do 封装订单提交的数据
 */

@Data
public class OrderSubmitVo {
    private Long addrId;

    // private Integer payType; // 支付方式，在线支付和到付

    private BigDecimal payPrice;

    private Boolean hasStock; // 是否有库存

    private String orderToken; // 防重令牌


}
