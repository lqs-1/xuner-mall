package com.lqs.mall.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author 李奇凇
 * @moduleName MallSessionConfiguration
 * @date 2022/10/25 上午10:06
 * @do 配置Session共享
 */

@Configuration
public class MallSessionConfiguration {


    // cookie 序列化器 cookie的一些设置
    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();

        defaultCookieSerializer.setDomainName("mall.com"); // 设置域名，子域都有效果
        defaultCookieSerializer.setCookieName("MALLSESSION"); // 设置这个(session)的Cookie名字,前端控制台展示的名字
        defaultCookieSerializer.setCookieMaxAge(60*60*24); // 设置session得过期时间，单位为秒

        return defaultCookieSerializer;
    }

    @Bean
    // Redis 序列化器, 存储在redis中的格式
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }

}
