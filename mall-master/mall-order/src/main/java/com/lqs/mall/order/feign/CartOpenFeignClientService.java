package com.lqs.mall.order.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 李奇凇
 * @moduleName CartOpenFeignClientService
 * @date 2022/10/28 下午2:34
 * @do 调用mall-cart的接口
 */

@FeignClient("mall-cart")
public interface CartOpenFeignClientService {

    @GetMapping("currentUserCartItem")
    R currentUserCartItem();

}
