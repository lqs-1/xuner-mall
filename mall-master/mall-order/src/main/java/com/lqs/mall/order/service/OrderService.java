package com.lqs.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.order.entity.OrderEntity;
import com.lqs.mall.order.vo.OrderConfirmVo;
import com.lqs.mall.order.vo.OrderSubmitVo;
import com.lqs.mall.order.vo.PayVo;
import com.lqs.mall.order.vo.SubmitOrderResponseVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:45
 */

public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo getConfirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo orderSubmitVo);

    OrderEntity requestOrderStatus(String orderSn);

    void orderAutoCancel(OrderEntity order);

    PayVo constructOrderPayData(String orderSn);

    OrderEntity requestAndAlterOrderByOrderSn(String orderSn);

    PageUtils requestOrderPage(Map<String, Object> param);

    void deleteOder(String orderSn);
}

