package com.lqs.mall.ware.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李奇凇
 * @moduleName OrderOpenFeignClientService
 * @date 2023/1/11 下午3:04
 * @do 调用订单服务得api
 */

@FeignClient("mall-order")
public interface OrderOpenFeignClientService {

    @GetMapping(value = "order/status/{orderSn}")
    R requestOrderStatus(@PathVariable String orderSn);

}
