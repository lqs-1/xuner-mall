package com.lqs.mall.order.config;

import com.lqs.mall.order.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李奇凇
 * @moduleName OrderViewControllerConfiguration
 * @date 2022/10/28 上午10:53
 * @do order相关的一些Mvc配置
 */

@Configuration
public class OrderViewControllerConfiguration implements WebMvcConfigurer {

    // 将用户登录拦截器添加到mvc中
    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**");
    }
}
