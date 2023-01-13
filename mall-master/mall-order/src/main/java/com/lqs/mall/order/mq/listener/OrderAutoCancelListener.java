package com.lqs.mall.order.mq.listener;

import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.order.entity.OrderEntity;
import com.lqs.mall.order.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 李奇凇
 * @moduleName OrderAutoCancelListener
 * @date 2023/1/11 下午1:19
 * @do 订单取消监听
 */

@Component
@RabbitListener(queues = Constant.ORDER_CANCEL_INVOKE_QUEUE)
public class OrderAutoCancelListener {

    @Autowired
    private OrderService orderService;

    /**
     * 自动取消订单 根据条件 取消超时订单
     * @param order
     * @param message
     * @param channel
     * @throws IOException
     */

    @RabbitHandler
    public void orderAutoCancelHandle(OrderEntity order, Message message, Channel channel) throws IOException {

        try{
            // 程序代码
            orderService.orderAutoCancel(order);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }



    }



}
