package com.lqs.mall.common.to.mq.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李奇凇
 * @moduleName OrderSnTO
 * @date 2023/1/11 下午7:06
 * @do 订单取消之后 传递订单号 解锁库存
 */

@Data
public class OrderSnTO implements Serializable {

   private String orderSn;

}
