package com.lqs.mall.coupon.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 李奇凇
 * @moduleName MybatisPlusPaginationInterceptor
 * @date 2023/1/13 下午5:16
 * @do mybatisPlus分页拦截器
 */

@EnableTransactionManagement
@Configuration
public class MybatisPlusPaginationInterceptor {


    @Bean
    // 引入分页插件
    public PaginationInterceptor paginationInnerInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面最大页后的操作，true调回首页，默认false，false为继续请求
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认是500， -1为不限制
        paginationInterceptor.setLimit(-1);
        return paginationInterceptor;
    }

}
