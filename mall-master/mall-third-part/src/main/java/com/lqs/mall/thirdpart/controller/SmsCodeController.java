package com.lqs.mall.thirdpart.controller;

import com.lqs.mall.common.utils.R;
import com.lqs.mall.thirdpart.utils.AliyunSmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李奇凇
 * @moduleName SmsCodeController
 * @date 2022/10/19 下午6:08
 * @do 短信验证码发送的controller
 */
@RestController
@RequestMapping("sms")
public class SmsCodeController {

    @Autowired
    private AliyunSmsUtils aliyunSmsUtils;

    @GetMapping("sendSmsCode")
    public R sendSmsCode(@RequestParam("mobileCode") String mobileCode, @RequestParam("smsCode") String smsCode){
        return aliyunSmsUtils.sendSmsCode(mobileCode, smsCode);
    }


}
