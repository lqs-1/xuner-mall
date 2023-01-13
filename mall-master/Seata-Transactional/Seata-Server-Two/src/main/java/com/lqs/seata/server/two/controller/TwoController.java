package com.lqs.seata.server.two.controller;

import com.lqs.mall.common.utils.R;
import com.lqs.seata.server.two.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李奇凇
 * @moduleName TwoController
 * @date 2023/1/8 下午6:38
 * @do 第二个微服务控制器
 */

@RestController
@RequestMapping("two")
public class TwoController {

    @Autowired
    private UserService userService;

    @GetMapping("alter")
    public R alterData(){
        // 程序代码
        userService.alterUser();

        System.out.println("two完成");
        return R.ok(10000, "ok");
    }


}
