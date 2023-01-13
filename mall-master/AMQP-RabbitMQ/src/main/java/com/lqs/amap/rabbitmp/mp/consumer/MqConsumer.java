package com.lqs.amap.rabbitmp.mp.consumer;

import com.lqs.amap.rabbitmp.common.Constant;
import com.lqs.amap.rabbitmp.entity.StudentEntity;
import com.lqs.amap.rabbitmp.entity.UserEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 李奇凇
 * @moduleName MqConsumer
 * @date 2022/12/30 下午6:01
 * @do 模拟就收处理消息的类
 */

@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = Constant.QUEUE_TWO), // 监听的队列，本来原始的创建方式不用写出来，但是这种方式需要写，因为如果没有会创建
        exchange = @Exchange(name = Constant.EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC), // 监听的交换机，如果没有会创建
         key = {Constant.ROUTE_KEY_TWO})) // 监听的route-key
public class MqConsumer {

    /**
     * RabbitListener(放在方法上):
     *      第一种用法是直接关联队列，要求消息队列已经存在
     *      第二种就是声明一个绑定关系，交换机、队列、route-key如果不存在就创建
     *         RabbitListener的绑定关系不管是哪种交换机，注意需要改动的就是key，因为topic是需要写通配符的
     *
     * RabbitHandler(获取消息处理器)：
     *      这个注解必须和RabbitListener配合使用，但是RabbitListener必须放在类上，RabbitHandler必须放在处理的方法上
     *      如果这个类中有多个方法来处理这个消息，那么RabbitHandler就会获取到消息，然后判断消息类型，再决定交给哪个方法处理
     * @param user
     */


    // @RabbitListener(queues = {"queue1", "queue2"})

    // @RabbitListener(bindings = @QueueBinding(value = @Queue(name = Constant.QUEUE_THREE),
    //         exchange = @Exchange(name = Constant.EXCHANGE_FANOUT, type = ExchangeTypes.FANOUT),
    //         key = {Constant.ROUTE_KEY_THREE}))
    @RabbitHandler
    public void MessageReceiveMethodOne(UserEntity user, Message message, Channel channel) throws IOException {

//        System.out.println(user + "用户");
//        System.out.println(message + "用户");
//        System.out.println(channel + "用户");

        // 手动签收,非批量模式
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        if (user.getId() % 2l == 0l){
            channel.basicAck(deliveryTag, false);
        }else {
            // 如果接收之后发现自己处理不了，可以退货,第二个表示是否批量拒签，第三个参数表示是否重新入队
            channel.basicNack(deliveryTag, false, true);
        }
    }

    @RabbitHandler
    public void MessageReceiveMethodTwo(StudentEntity student, Message message, Channel channel) throws IOException {
//
//        System.out.println(student + "学生");
//        System.out.println(message + "学生");
//        System.out.println(channel + "学生");

        // 手动签收,非批量模式
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        if (student.getId() % 2l == 0l){
            channel.basicAck(deliveryTag, false);
        }else {
            // 如果接收之后发现自己处理不了，可以退货,第二个表示是否批量拒签，第三个参数表示是否重新入队
            channel.basicNack(deliveryTag, false, true);
        }
    }

}
