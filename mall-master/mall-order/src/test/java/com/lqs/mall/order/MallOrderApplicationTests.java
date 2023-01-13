package com.lqs.mall.order;

import com.lqs.mall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class MallOrderApplicationTests {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     *创建Exchange、Queue、Binding
     *      使用AmqpAdmin创建、
     * 接收消息
     */
    @Test
    public void createExchange() {

        // 新建交换机实例
        DirectExchange directExchange = new DirectExchange(
                "hello-direct-exchange", // 交换机名字
                true, // 是否持久化
                false // 是否自动删除
        );

        amqpAdmin.declareExchange(directExchange); // 声明一个交换机,

        log.info("交换机创建完成");
    }


    @Test
    public void createQueue() {

        // 新建队列实例
        Queue queue = new Queue(
                "hello-common-queue", // 队列名字
                true, // 是否持久化
                false, // 是否排他，只能有一个能连接上
                false // 是否自动删除
        );

        amqpAdmin.declareQueue(queue); // 声明一个交换机,

        log.info("队列创建成功");
    }


    @Test
    public void createBinding() {

        // 新建队列实例
        Binding binding = new Binding(
                "hello-common-queue", // 目的地,就是交换机要绑定的东西
                Binding.DestinationType.QUEUE, // 目的地类型，交换机和队列
                "hello-direct-exchange", // 交换机
                "hello", // 路由键
                null // 自定义参数
        );

        amqpAdmin.declareBinding(binding); // 声明一个交换机,

        log.info("绑定创建成功");
    }



    @Test
    public void sendMessage(){

        // 准备要发送的消息
        OrderReturnReasonEntity orderReturnReasonEntity = new OrderReturnReasonEntity();
        orderReturnReasonEntity.setCreateTime(new Date());
        orderReturnReasonEntity.setId(1L);
        orderReturnReasonEntity.setName("lqs");
        orderReturnReasonEntity.setSort(1);
        orderReturnReasonEntity.setStatus(1);


        rabbitTemplate.convertAndSend(
                "hello-direct-exchange", // 发给哪一个交换机
                "hello", // route-key
                orderReturnReasonEntity // 要发送的数据
        );
    }

}
