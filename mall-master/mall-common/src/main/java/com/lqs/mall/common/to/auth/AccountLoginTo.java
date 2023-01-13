package com.lqs.mall.common.to.auth;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author 李奇凇
 * @moduleName AccountLoginTo
 * @date 2022/10/24 下午1:37
 * @do 普通注册登录数据对象
 */

@Data
public class AccountLoginTo {

    @Length(min = 4, max = 12, message = "用户名长度不符合")
    @NotBlank(message = "用户名必须填写")
    private String loginAccount;

    @Length(min = 4, max = 12, message = "密码长度不符合")
    @NotBlank(message = "密码必须填写")
    private String password;

}
