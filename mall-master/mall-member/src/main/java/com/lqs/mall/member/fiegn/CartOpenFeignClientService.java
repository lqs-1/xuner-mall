package com.lqs.mall.member.fiegn;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName CartOpenFeignClientService
 * @date 2023/1/12 下午4:22
 * @do 远程调用cart得api
 */
@FeignClient("mall-cart")
public interface CartOpenFeignClientService {

    @PostMapping("delete/{userId}/cartItem")
    @ResponseBody
    R deleteCartItemBySkuIds(@PathVariable String userId, @RequestBody List<Long> skuIds);


}
