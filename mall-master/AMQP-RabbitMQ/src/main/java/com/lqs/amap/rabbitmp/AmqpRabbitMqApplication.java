package com.lqs.amap.rabbitmp;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AmqpRabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmqpRabbitMqApplication.class, args);
    }

    /**
     * mq得消息转换器
     * @return
     */
    @Bean
    public MessageConverter messageJsonConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
