package com.lqs.mall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author 李奇凇
 * @date 2022年07月31日 下午7:38
 * @do gateway跨域,webflux跨域
 */

@Configuration
public class CorsConfig {
    /**

     * webflux中的reactive的拦截器

     * @return

     */

    @Bean
    public CorsWebFilter corsWebFilter() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 允许cookies跨域

//        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，
//        *表示全部允许，在SpringMVC中，如果设成*，
//        会自动转成当前请求头中的Origin,在高版本之后不准设置为*,webmvc也是,要用addAllowedOriginPattern

        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部

        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了

        config.addAllowedMethod("*"); // 允许提交请求的方法，*表示全部允许

        config.addAllowedOriginPattern("*"); // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);

    }
}
