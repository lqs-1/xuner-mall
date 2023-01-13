package com.lqs.mall.cart.config;

import com.lqs.mall.cart.interceptor.CartUserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李奇凇
 * @moduleName CartViewControllerConfiguration
 * @date 2022/10/24 上午11:45
 * @do 试图返回配置
 */


@Configuration
public class CartViewControllerConfiguration implements WebMvcConfigurer {

    @Autowired
    private CartUserAuthInterceptor cartUserAuthInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("cart").setViewName("cartList");
        registry.addViewController("success").setViewName("success");
    }





    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartUserAuthInterceptor).addPathPatterns("/**");
    }
}
