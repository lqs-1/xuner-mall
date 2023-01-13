package com.lqs.mall.ware.mq.publisher;

import com.lqs.mall.common.constant.Constant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李奇凇
 * @moduleName WareStockReleasePublisher
 * @date 2023/1/11 下午1:23
 * @do 商品库存锁定成功发送消息给队列 ware-stock-time-delay-queue
 */

@Configuration
public class WareStockReleasePublisher<T> {


    @Autowired
    private RabbitTemplate rabbitClient;

    public void sendWareStockLockTimeDelayMessage(T message){

        /**
         * 发消息的时候写好发给哪个交换机，发给哪个route-key，要发送的消息就行了
         */
        rabbitClient.convertAndSend(Constant.WARE_STOCK_EXCHANGE, Constant.WARE_STOCK_TIME_DELAY_QUEUE_ROUTE_KEY, message);

    }

    // 创建队列和交换机以及绑定关系

    /**
     * 创建交换机,这个交换机上面绑定一个延时队列和一个普通队列
     *  这个交换机用于绑定死信队列和延时队列
     * @return
     */
    @Bean
    public Exchange wareStockExchange(){
        return new TopicExchange(Constant.WARE_STOCK_EXCHANGE, true, false);
    }

    /**
     * 创建库存得延时队列,配置项:
     *  这个队列是延时队列 每一个商品锁定了库存都会给这个队列发送消息
     *  延时到期绑定哪一个交换机
     *  延时到期绑定哪一个routeKey
     *  延时时间 毫秒
     *
     * @return
     */
    @Bean
    public Queue wareStockTimeDelayQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", Constant.WARE_STOCK_EXCHANGE);
        args.put("x-dead-letter-routing-key", Constant.WARE_STOCK_RELEASE_QUEUE_ROUTE_KEY);
        args.put("x-message-ttl", Constant.WARE_STOCK_TIME_DELAY_QUEUE_TTL_MILLI);
        return new Queue(Constant.WARE_STOCK_TIME_DELAY_QUEUE, true, false, false, args);
    }

    /**
     * 创建普通队列,用于接收延时队列过期得消息
     *  这个队列接收死信 用于给服务监听 做逻辑处理
     * @return
     */
    @Bean
    public Queue wareStockReleaseQueue(){
        return new Queue(Constant.WARE_STOCK_RELEASE_QUEUE, true, false, false);
    }

    /**
     * 创建绑定关系:延时队列和交换机得关联
     *  绑定延时队列
     * @return
     */
    @Bean
    public Binding wareStockDelayQueueBinding(){
        return new Binding(Constant.WARE_STOCK_TIME_DELAY_QUEUE, Binding.DestinationType.QUEUE, Constant.WARE_STOCK_EXCHANGE, Constant.WARE_STOCK_TIME_DELAY_QUEUE_ROUTE_KEY, null);
    }

    /**
     * 创建绑定关系:普通队列和交换机得关联
     *  绑定死信队列
     * @return
     */
    @Bean
    public Binding wareStockReleaseQueueBinding(){
        return new Binding(Constant.WARE_STOCK_RELEASE_QUEUE, Binding.DestinationType.QUEUE, Constant.WARE_STOCK_EXCHANGE, Constant.WARE_STOCK_RELEASE_QUEUE_ROUTE_KEY, null);
    }



    /**
     * 定制RabbitTemplate,将确认回调设置进去
     */
    @PostConstruct // MyRabbitConfig创建完成以后，执行这个方法
    public void initRabbitTemplate(){

        rabbitClient.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             * 消息抵达exchange执行这个回调
             * @param correlationData 回调的相关数据 当前消息的唯一关联数据(这个消息的唯一id)
             * @param ack 消息是否成功收到, 只要消息抵达了服务器就是true
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                // System.out.println("correlationData" + correlationData);
                // System.out.println("ack" + ack);
                // System.out.println("cause" + cause);
            }
        });



        rabbitClient.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {

            /**
             * 只要消息没有投递给指定的队列，就触发这个回调
             * @param returned 返回的消息和元数据
             */
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("returned" + returned);
            }
        });
    }


}
