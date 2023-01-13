package com.lqs.mall.order.feign;

import com.lqs.mall.common.utils.R;
import com.lqs.mall.order.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 李奇凇
 * @moduleName WareOpenFeignClientService
 * @date 2023/1/5 下午6:32
 * @do 远程调用ware系统api
 */

@FeignClient("mall-ware")
public interface WareOpenFeignClientService {


    @PostMapping("ware/waresku/lock/stock")
    R orderLockStock(@RequestBody WareSkuLockVo wareSkuLock);


}
