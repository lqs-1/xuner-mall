package com.lqs.mall.member.fiegn;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 李奇凇
 * @moduleName OrderOpenFeignClientService
 * @date 2023/1/12 下午1:45
 * @do 远程 调用 order服务得api
 */

@FeignClient("mall-order")
public interface OrderOpenFeignClientService {

    @PostMapping("order/{orderSn}/requestAndAlterOrder")
    R requestAndAlterOrder(@PathVariable String orderSn);

    @PostMapping("order/orderList")
    @ResponseBody
    public R requestOrderPage(@RequestBody Map<String, Object> param);
}
