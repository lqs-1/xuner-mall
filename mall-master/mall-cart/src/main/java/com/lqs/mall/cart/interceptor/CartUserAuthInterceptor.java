package com.lqs.mall.cart.interceptor;

import com.lqs.mall.cart.to.UserTo;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.auth.AccountRespTo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author 李奇凇
 * @moduleName CartUserAuthInterceptor
 * @date 2022/10/26 上午9:17
 * @do 在执行目标controller之前，判断用户的登录状态，并封装传递给controller的目标请求
 */

@Component
public class CartUserAuthInterceptor implements HandlerInterceptor {

    // ThreadLocal
    public static ThreadLocal<UserTo> userToThreadLocal = new ThreadLocal<>();


    /**
     * 在目标方法执行之前拦截
     *
     * 如果是第一次使用这个mall的购物车功能，都会给一个临时用户身份
     * 浏览器保存以后，每次访问都会带上这个cookie，过期时间一个月
     *
     * ThreadLocal： 同一个线程共享数据
     *
     * 登录了：session有
     * 没登录：按照cookie里带来的user-key来做
     * 第一个：如果没有临时用户，帮忙创建一个临时用户
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        AccountRespTo account = (AccountRespTo) session.getAttribute(Constant.ACCOUNT_SESSION_KEY);

        UserTo userTo = new UserTo();

        if (account != null){

            // 登录了
            userTo.setUserId(account.getId());

        }

        // 不管登没登录 看看是不是临时用户 因为可能在查看购物车的时候 合并临时购物车到登录用户购物车
        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constant.TEMP_USER_KEY)){
                    userTo.setUserKey(cookie.getValue());
                    userTo.setIsTempUser(true);
                }
            }
        }


        // 不是登录用户 也不是临时用户 直接分配一个
        if (userTo.getUserId() == null && userTo.getUserKey() == null){
            String userKey = UUID.randomUUID().toString();

            userTo.setUserKey(userKey);
            userTo.setIsTempUser(true);
        }

        // 在目标方法执行之前
        userToThreadLocal.set(userTo);

        return true;
    }


    /**
     * 在目标方法执行之后拦截
     *
     * 如果没有临时用户，也没有登录，就给临时用户设置cookie
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        UserTo userTo = userToThreadLocal.get();

        // 就添加cookie, 存在临时用户就是重置cookie有效期，没有就是添加cookie
        if (userTo.getIsTempUser()){
            Cookie cookie = new Cookie(Constant.TEMP_USER_KEY, userTo.getUserKey());

            cookie.setDomain("mall.com");

            cookie.setMaxAge(Constant.TEMP_USER_KEY_TIMEOUT);

            response.addCookie(cookie);
        }


    }
}
