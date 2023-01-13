package com.lqs.seata.server.one.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 李奇凇
 * @moduleName TwoFeignClientService
 * @date 2023/1/8 下午6:39
 * @do 调用two微服务得接口
 */

@FeignClient("two")
public interface TwoFeignClientService {


    @GetMapping("two/alter")
    R alter();




}
