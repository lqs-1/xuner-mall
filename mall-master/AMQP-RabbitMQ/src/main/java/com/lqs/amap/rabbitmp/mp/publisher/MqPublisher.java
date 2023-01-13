package com.lqs.amap.rabbitmp.mp.publisher;

import com.lqs.amap.rabbitmp.common.Constant;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author 李奇凇
 * @moduleName MqPublisher
 * @date 2022/12/30 下午5:59
 * @do 模拟发送消息的类
 */

@Configuration
public class MqPublisher<T> {

    @Autowired
    private RabbitTemplate rabbitClient;

    public void sendMessage(T message){

        /**
         * 发消息的时候写好发给哪个交换机，发给哪个route-key，要发送的消息就行了
         */

        for (int i = 0; i < 100; i++) {
            rabbitClient.convertAndSend(Constant.EXCHANGE_TOPIC, "key.few", message);
        }

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
                System.out.println("correlationData" + correlationData);
                System.out.println("ack" + ack);
                System.out.println("cause" + cause);
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
