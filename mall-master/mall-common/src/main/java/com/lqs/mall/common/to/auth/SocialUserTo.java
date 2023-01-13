package com.lqs.mall.common.to.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李奇凇
 * @moduleName SocialUser
 * @date 2022/10/24 下午3:50
 * @do 社交登录access_token换取数据对象
 */

@Data
public class SocialUserTo implements Serializable {

    private String access_token;

    private String remind_in;

    private Long expires_in;

    private String uid;

    private String isRealName;


}
