package com.lqs.amap.rabbitmp.mp.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李奇凇
 * @moduleName MyRabbitConfig
 * @date 2023/1/2 上午10:24
 * @do RabbitMQ配置
 */

@Configuration
public class MyRabbitConfig {


    /**
     * 使用JSON序列化机制 进行消息转换
     * @return
     */

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
