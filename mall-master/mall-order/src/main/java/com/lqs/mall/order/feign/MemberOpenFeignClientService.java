package com.lqs.mall.order.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李奇凇
 * @moduleName MemberOpenFeignClientService
 * @date 2022/10/28 下午2:34
 * @do 调用mall-member的接口
 */

@FeignClient("mall-member")
public interface MemberOpenFeignClientService {


    @GetMapping("member/memberreceiveaddress/{memberId}/addressList")
    R getAddressList(@PathVariable Long memberId);


    @GetMapping("member/memberreceiveaddress/{addressId}/address")
    R getReceiveAddress(@PathVariable Long addressId);


}
