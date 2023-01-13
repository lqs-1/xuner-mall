package com.lqs.mall.order.vo;

import com.lqs.mall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @author 李奇凇
 * @moduleName SubmitOrderResponseVo
 * @date 2022/10/28 下午8:21
 * @do 点击提交订单后下单得返回对象
 */
@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    private Integer statusCode; // 下单得状态码 0表示成功

}
