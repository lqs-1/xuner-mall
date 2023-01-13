package com.lqs.mall.auth.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李奇凇
 * @moduleName AuthViewControllerConfiguration
 * @date 2022/10/19 上午11:13
 * @do 和controller包下面的功能一样，跳转页面,只跳转页面
 */
// @Configuration
public class AuthViewControllerConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("login").setViewName("login");
        // registry.addViewController("register").setViewName("register");
    }
}
