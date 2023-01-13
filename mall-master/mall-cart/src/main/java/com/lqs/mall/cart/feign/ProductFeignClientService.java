package com.lqs.mall.cart.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李奇凇
 * @moduleName ProductFeignClientService
 * @date 2022/10/26 下午1:14
 * @do 调用product服务的接口
 */

@FeignClient("mall-product")
public interface ProductFeignClientService {

    @GetMapping("product/skuinfo/info/{skuId}")
    R getSkuInfo(@PathVariable("skuId") Long skuId);


    @GetMapping("product/skusaleattrvalue/saleAttrValues/{skuId}")
    R getSkuSaleAttrValues(@PathVariable Long skuId);


    @GetMapping("product/skuinfo/{skuId}/price")
    R getLatestPrice(@PathVariable Long skuId);


}
