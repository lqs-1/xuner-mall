package com.lqs.mall.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 李奇凇
 * @moduleName ProductThreadPoolConfig
 * @date 2022/10/19 上午4:17
 * @do 线程池配置
 */
@Configuration
public class ProductThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigurationProperties threadPoolConfigurationProperties){
        return new ThreadPoolExecutor(threadPoolConfigurationProperties.getCoreSize(),
                threadPoolConfigurationProperties.getMaxSize(),
                threadPoolConfigurationProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

    }


}
