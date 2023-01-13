package com.lqs.mall.search.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李奇凇
 * @moduleName ProductFeignClientService
 * @date 2022/10/15 下午1:31
 * @do 调用product服务的接口
 */

@FeignClient("mall-product")
public interface ProductFeignClientService {

    // 获取product——attrName
    @GetMapping("product/attr/getAttrName/{attrId}")
    R getAttrName(@PathVariable("attrId") Long attrId);


}
