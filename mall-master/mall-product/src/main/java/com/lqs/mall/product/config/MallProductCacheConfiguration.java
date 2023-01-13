package com.lqs.mall.product.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 李奇凇
 * @moduleName MallProductCacheConfiguration
 * @date 2022/10/4 上午9:55
 * @do 自定义SpringCache配置
 */
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class MallProductCacheConfiguration {

    /**
     * application的配置没有用上：
     * @EnableConfigurationProperties(CacheProperties.class)和这个文件绑定配置，因为这个文件读取了application的配置
     * 注入CacheProperties
     *
     * @return
     */

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){
        // 使用默认配置，修改部分配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        // 修改保存key的序列化机制
        config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        // 修改保存value的序列化机制
        config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        // 将配置文件中的所有配置都生效
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }

        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }

        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }

        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return config;
    }

}
