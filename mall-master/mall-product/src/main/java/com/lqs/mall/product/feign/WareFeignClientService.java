package com.lqs.mall.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author 李奇凇
 * @moduleName WareFeignClientService
 * @date 2022/9/28 下午5:43
 * @do 调用ware服务的api
 */

@FeignClient("mall-ware")
public interface WareFeignClientService {

    @PostMapping("ware/waresku/hasstock")
    Map<Long, Boolean>  getSkuHasStock(@RequestBody List<Long> skuIds);


}
