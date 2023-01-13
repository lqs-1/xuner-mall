package com.lqs.mall.product.feign;

import com.lqs.mall.common.to.SkuReductionTo;
import com.lqs.mall.common.to.SpuBoundsTo;
import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 李奇凇
 * @date 2022/8/31 下午5:22
 * @do 调用优惠服务的接口
 */
@FeignClient("mall-coupon")
public interface CouponFeignClientService {


    @PostMapping("coupon/spubounds/save")
    // @RequiresPermissions("coupon:spubounds:save")
    R spuBoundsSave(@RequestBody SpuBoundsTo spuBounds);


    @PostMapping("coupon/skufullreduction/save")
    R saveSkuReduction(@RequestBody SkuReductionTo reductionTo);



}
