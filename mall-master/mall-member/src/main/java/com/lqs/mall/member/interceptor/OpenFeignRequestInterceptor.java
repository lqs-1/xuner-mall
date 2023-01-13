package com.lqs.mall.member.interceptor;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李奇凇
 * @moduleName OpenFeignRequestInterceptor
 * @date 2022/10/28 下午4:03
 * @do 本服务要通过feign调用其他的服务的时候拦截到 做处理
 */

@Configuration
public class OpenFeignRequestInterceptor {


    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor(){
//        return new RequestInterceptor() {
//            @Override
//            public void apply(RequestTemplate requestTemplate) {
//
//            }
//        }

        return requestTemplate -> {
            // RequestContextHolder拿到刚进来的这个请求
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest(); // 老请求

            if (request != null){

                // 同步请求头数据 cookie
                requestTemplate.header("Cookie", request.getHeader("Cookie")); // 给新请求添加cookie
            }

            // 异步的时候还是会丢失老请求 在异步开始之前获取老请求 在进入异步之后给新请求设置信息


        };
    }



}
