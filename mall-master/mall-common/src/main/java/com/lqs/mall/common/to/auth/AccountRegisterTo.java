package com.lqs.mall.common.to.auth;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author 李奇凇
 * @moduleName AccountTo
 * @date 2022/10/21 上午10:46
 * @do 普通注册登录数据对象
 */

@Data
public class AccountRegisterTo {

    @Length(min = 4, max = 12, message = "用户名长度不符合")
    @NotBlank(message = "用户名必须填写")
    private String userName;

    @Length(min = 4, max = 12, message = "密码长度不符合")
    @NotBlank(message = "密码必须填写")
    private String passWord;

    @Pattern(regexp = "^1[3-9]{1}[0-9]{9}$", message = "手机号格式不正确")
    @NotBlank(message = "手机号必须填写")
    private String mobileCode;

    @NotBlank(message = "验证码必须填写")
    private String smsCode;
}
