package com.lqs.mall.auth.feign;

import com.lqs.mall.common.to.auth.AccountLoginTo;
import com.lqs.mall.common.to.auth.AccountRegisterTo;
import com.lqs.mall.common.to.auth.SocialUserTo;
import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 李奇凇
 * @moduleName MemberFeignClientService
 * @date 2022/10/21 下午1:23
 * @do 调用member服务的接口
 */

@FeignClient("mall-member")
public interface MemberFeignClientService {

    @PostMapping("member/member/commonAccountRegister")
    R commonAccountRegister(@RequestBody AccountRegisterTo accountRegisterTo);


    @PostMapping("member/member/commonAccountLogin")
    R commonAccountLogin(@RequestBody AccountLoginTo accountLoginTo);

    @PostMapping("member/member/oauth2/socialAccountAutoLogin")
    R socialAccountAutoLogin(@RequestBody SocialUserTo socialUserTo);


}
