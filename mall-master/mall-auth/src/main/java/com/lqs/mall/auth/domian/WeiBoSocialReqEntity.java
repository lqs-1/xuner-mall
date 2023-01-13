package com.lqs.mall.auth.domian;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 李奇凇
 * @moduleName SocialReqEntity
 * @date 2022/10/24 下午6:23
 * @do 用于换取access_token的配置项
 */
@ConfigurationProperties(prefix = "oauth2.weibo")
@Data
@Component
public class WeiBoSocialReqEntity {

    private String appKey;

    private String appSecret;

    private String grantType;

    private String redirectUri;

}
