package com.lqs.mall.member.fiegn;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 李奇凇
 * @moduleName WareOpenFeignClientService
 * @date 2023/1/12 下午2:28
 * @do 远程调用ware服务得api
 */

@FeignClient("mall-ware")
public interface WareOpenFeignClientService {

    @PostMapping("ware/waresku/ware/{orderSn}/deduction/stock")
    R deductionWareStock(@PathVariable String orderSn);



}
