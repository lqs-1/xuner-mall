package com.lqs.mall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lqs.mall.auth.domian.WeiBoSocialReqEntity;
import com.lqs.mall.auth.feign.MemberFeignClientService;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.auth.AccountRespTo;
import com.lqs.mall.common.to.auth.SocialUserTo;
import com.lqs.mall.common.utils.HttpUtils;
import com.lqs.mall.common.utils.R;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李奇凇
 * @moduleName OAuth2WeiboAutoLoginAccountController
 * @date 2022/10/24 下午3:07
 * @do 社交登录方式的controller
 */
@Controller
public class OAuth2WeiboAutoLoginAccountController {

    @Autowired
    private MemberFeignClientService memberFeignClientService;

    @Autowired
    private WeiBoSocialReqEntity weiBoSocialReqEntity;


    /**
     * 获取登录页面 如果登录过了 直接跳首页
     * @param session
     * @return
     */
    @GetMapping("login")
    public String login(HttpSession session){
        Object account = session.getAttribute(Constant.ACCOUNT_SESSION_KEY);

        if (account != null){
            return "redirect:http://www.mall.com";
        }

        return "login";
    }



    /**
     * 获取这测页面 如果登录过了 直接跳首页
     * @param session
     * @return
     */
    @GetMapping("register")
    public String register(HttpSession session){
        Object account = session.getAttribute(Constant.ACCOUNT_SESSION_KEY);

        if (account != null){
            return "redirect:http://www.mall.com";
        }

        return "register";
    }


    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:http://www.mall.com";

    }


    // http://auth.mall.com/oauth2/weibo/autoLogin  回调
    // http://auth.mall.com/oauth2/weibo/autoLogin?code=884a70f0d7ea15f1cc26c04da2cadc3b  换取access_token，用这个code

    @GetMapping("oauth2/weibo/autoLogin")
    public String weiboAutoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {

        // 根据code换取access_token
        Map<String, String> queryParam = new HashMap<>();
        // 构建请求参数
        queryParam.put(Constant.WEIBO_CLIENT_ID, weiBoSocialReqEntity.getAppKey());
        queryParam.put(Constant.WEIBO_CLIENT_SECRET, weiBoSocialReqEntity.getAppSecret());
        queryParam.put(Constant.WEIBO_GRANT_TYPE, weiBoSocialReqEntity.getGrantType());
        queryParam.put(Constant.WEIBO_REDIRECT_URI, weiBoSocialReqEntity.getRedirectUri());
        queryParam.put(Constant.WEIBO_CODE, code);

        HttpResponse httpResponse = HttpUtils.doPost(Constant.OAUTH2_WEIBO_HOST, Constant.OAUTH2_WEIBO_REQUEST_ACCESS_TOKEN_PATH, "post", new HashMap<String, String>() , null, queryParam);

        // 处理响应数据
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            // 获取到access_token
            String jsonString = EntityUtils.toString(httpResponse.getEntity());

            SocialUserTo socialUserTo = JSON.parseObject(jsonString, SocialUserTo.class);

            // 知道了是哪一个社交用户
            // 如果是第一次进网站，那么就自动注册，否则就是登录，自动化的
            R socialAccountLongResponse = memberFeignClientService.socialAccountAutoLogin(socialUserTo);

            if (socialAccountLongResponse.parseCode() >= 10000 && socialAccountLongResponse.parseCode() < 20000){
                AccountRespTo account = socialAccountLongResponse.parseType(new TypeReference<AccountRespTo>(){}, "account");
                // 存储session
                session.setAttribute(Constant.ACCOUNT_SESSION_KEY, account);

                session.setMaxInactiveInterval(60*60*24); // 设置过期时间，单位秒，0或者负数为不过期

                // 登录成功就跳到首页
                return "redirect:http://www.mall.com";
            }


        }

        return "redirect:http://auth.mall.com/login";


    }




}
