package com.lqs.amap.rabbitmp.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author 李奇凇
 * @moduleName UserEntity
 * @date 2022/12/30 下午6:05
 * @do 用户实体类
 */

@Data
@ToString
public class UserEntity {

    private Long id;

    private String userName;

    private Integer age;

    private char gender;

}
