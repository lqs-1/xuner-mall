package com.lqs.mall.cart.to;

import lombok.Data;

/**
 * @author 李奇凇
 * @moduleName UserTo
 * @date 2022/10/26 上午9:35
 * @do 用户访问购物车的时候 拦截器组装的数据 用于传递给Controller
 */

@Data
public class UserTo {

    private Long userId;

    private String userKey;

    private Boolean isTempUser = false;

}
