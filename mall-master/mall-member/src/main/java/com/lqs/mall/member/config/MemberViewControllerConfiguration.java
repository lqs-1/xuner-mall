package com.lqs.mall.member.config;

import com.lqs.mall.member.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李奇凇
 * @moduleName CartViewControllerConfiguration
 * @date 2022/10/24 上午11:45
 * @do 试图返回配置
 */


@Configuration
public class MemberViewControllerConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;





    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**");
    }
}
