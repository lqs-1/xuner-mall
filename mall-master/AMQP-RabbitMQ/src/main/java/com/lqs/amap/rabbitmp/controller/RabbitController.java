package com.lqs.amap.rabbitmp.controller;

import com.lqs.amap.rabbitmp.entity.StudentEntity;
import com.lqs.amap.rabbitmp.entity.UserEntity;
import com.lqs.amap.rabbitmp.mp.publisher.MqPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李奇凇
 * @moduleName RabbitController
 * @date 2022/12/30 下午6:02
 * @do rabbitMq的控制器测试类
 */


@RestController
public class RabbitController {

    @Autowired
    MqPublisher mqPublisher;

    @GetMapping("user")
    public String user(){

        UserEntity user = new UserEntity();

        user.setUserName("lqs");

        user.setAge(12);

        user.setGender('w');

        user.setId(1L);

        mqPublisher.sendMessage(user);

        return "user ok";
    }


    @GetMapping("student")
    public String student(){

        StudentEntity student = new StudentEntity();

        student.setUserName("lqs");

        student.setAge(12);

        student.setGender('w');

        student.setId(1L);

        mqPublisher.sendMessage(student);

        return "student ok";
    }



}
