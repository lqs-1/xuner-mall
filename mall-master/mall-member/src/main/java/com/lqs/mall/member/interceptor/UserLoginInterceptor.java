package com.lqs.mall.member.interceptor;

import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.auth.AccountRespTo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李奇凇
 * @moduleName UserLoginInterceptor
 * @date 2022/10/28 上午10:51
 * @do 到达目标请求之前 检测用户是否登录
 */


@Component
public class UserLoginInterceptor implements HandlerInterceptor {


    // 类似与flask里的g对象, 实现同一个线程共享数据（同一次请求）
    public static ThreadLocal<AccountRespTo> user = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        /**
         * 因为用户请求都需要登录,所以配置配置是给第三方配置得
         *  因为我在做延迟队列解锁库存得时候,需要查询order服务得订单
         *  在发送远程调用得时候,会出现异常,这个异常就是出现在这里
         *  因为order服务需要登录了才可以访问,而程序监听到mq得数据,是没有用户身份得
         *  所以我们可以配置过滤掉这个路径,遇到这个路径直接放行
         */


        if (new AntPathMatcher().match("/member/**", request.getRequestURI())) {
            return true;
        }


        AccountRespTo account = (AccountRespTo) request.getSession().getAttribute(Constant.ACCOUNT_SESSION_KEY);


        if (account != null){

            user.set(account);

            return true;
        }else {
            response.sendRedirect("http://auth.mall.com/login");

            return false;
        }

    }
}
