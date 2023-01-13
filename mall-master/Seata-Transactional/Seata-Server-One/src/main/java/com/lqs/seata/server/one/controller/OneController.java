package com.lqs.seata.server.one.controller;

import com.lqs.seata.server.one.domain.UserEntity;
import com.lqs.seata.server.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName OneController
 * @date 2023/1/8 下午6:37
 * @do 第一个微服务控制器
 */

@RestController
@RequestMapping("one")
public class OneController {

    @Autowired
    private UserService userService;

    @GetMapping("alter")
    public String alterUser(){
        try{
            // 程序代码
            userService.alterUser();

            System.out.println("one完成");
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码


            return "error";
        }finally{
            // 程序代码

        }

    }


    @GetMapping("findAll")
    public String findAll(){
        List<UserEntity> userEntityList = userService.getBaseMapper().selectList(null);

        for (UserEntity userEntity : userEntityList) {
            System.out.println(userEntity);
        }


        return "ok";
    }


}
