package com.lqs.mall.ware.mq.listener;

import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.mq.order.OrderSnTO;
import com.lqs.mall.common.to.mq.ware.WareStockLockTo;
import com.lqs.mall.ware.service.WareSkuService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 李奇凇
 * @moduleName WareStockReleaseListener
 * @date 2023/1/11 下午1:19
 * @do 监听ware-stock-release-queue库存解锁队列
 */

@Component
@RabbitListener(queues = Constant.WARE_STOCK_RELEASE_QUEUE)
public class WareStockReleaseListener {

    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 根据条件解锁库存(自动解锁 使用mq监听得到得数据 根据条件自行判断) 这是一个补偿方法
     * @param wareStockLockTo
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void releaseWareStockHandle(WareStockLockTo wareStockLockTo, Message message, Channel channel) throws IOException {

        try{
            // 程序代码
            wareSkuService.releaseWareStock(wareStockLockTo);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }

    }

    /**
     * 根据条件解锁库存(自动解锁 使用mq监听得到得数据 根据条件自行判断) 这个是在订单自动删除之后直接发送得解锁消息
     * @param args 存放orderSn
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void releaseWareStockByOrderCancelPostHandle(OrderSnTO orderSn, Message message, Channel channel) throws IOException {

        try{
            // 程序代码
            wareSkuService.releaseWareStockByOrderCancelPost(orderSn.getOrderSn());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }finally{
            // 程序代码

        }

    }

}
