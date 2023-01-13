package com.lqs.mall.order.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李奇凇
 * @moduleName ProductOpenFeignClientService
 * @date 2023/1/5 上午10:11
 * @do 商品服务远程调用接口
 */

@FeignClient("mall-product")
public interface ProductOpenFeignClientService {

    @GetMapping("product/spuinfo/skuId/{skuId}")
    R getSpuInfoBySkuId(@PathVariable Long skuId);

    @GetMapping("product/brand/brandName/{brandId}")
    R getBrandNameByBrandId(@PathVariable Long brandId);

}
