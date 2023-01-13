package com.lqs.mall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 李奇凇
 * @moduleName ThreadPoolConfigurationProperties
 * @date 2022/10/19 上午1:54
 * @do 线程池配置项
 */

@ConfigurationProperties(prefix = "mall.thread.pool")
@Component // 放在容器中
@Data
public class ThreadPoolConfigurationProperties {

    private Integer coreSize; // 核心线程大小

    private Integer maxSize; // 最大线程大小

    private Integer keepAliveTime;  // 休眠时长，空闲线程多久销毁（maxSize-coreSize这部分）


}
