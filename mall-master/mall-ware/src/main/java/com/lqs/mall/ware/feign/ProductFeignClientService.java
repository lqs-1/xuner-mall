package com.lqs.mall.ware.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李奇凇
 * @moduleName ProductFeignClientService
 * @date 2022/9/24 上午11:07
 * @do 调用商品服务的接口
 */

@FeignClient("mall-product")
public interface ProductFeignClientService {

    @GetMapping("product/skuinfo/skuName/{skuId}")
    R skuName(@PathVariable("skuId") Long skuId);

}
