package com.lqs.mall.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 李奇凇
 * @date 2022年07月31日 下午9:30
 * @do 远程调用product服务里面的功能
 */

// 写清楚被调用的微服务的名字
@FeignClient("mall-product")
public interface ProductOpenFeignService {

    // 这个接口中所有的方法都和被调用的应用中的中对应的方法同名，请求方法和请求路径也是一样的，在使用的时候就和调用service一模一样
    // 如果被调用的其他微服务有参数，那么照抄



}
