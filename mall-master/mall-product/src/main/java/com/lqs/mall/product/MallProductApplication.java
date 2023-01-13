package com.lqs.mall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 李奇凇
 * @date 2022年07月30日 下午10:07
 * @do 启动类
 */

@EnableRedisHttpSession
// 服务注册发现
@EnableDiscoveryClient
@EnableCaching
@EnableFeignClients("com.lqs.mall.product.feign")
@SpringBootApplication
public class MallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProductApplication.class, args);
    }

}
