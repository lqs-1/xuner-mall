package com.lqs.amap.rabbitmp.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author 李奇凇
 * @moduleName StudentEntity
 * @date 2022/12/30 下午6:06
 * @do 学生实体类
 */

@Data
@ToString
public class StudentEntity {

    private Long id;

    private String userName;

    private Integer age;

    private char gender;

}
