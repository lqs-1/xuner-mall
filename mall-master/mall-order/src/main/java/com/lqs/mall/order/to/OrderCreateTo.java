package com.lqs.mall.order.to;

import com.lqs.mall.order.entity.OrderEntity;
import com.lqs.mall.order.entity.OrderItemEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName OrderCreateTo
 * @date 2023/1/4 上午9:33
 * @do 创建订单实体类
 */
@Data
public class OrderCreateTo {

    private OrderEntity order;

    private List<OrderItemEntity> orderItems;

}
