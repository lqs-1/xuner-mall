package com.lqs.seata.server.one.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 李奇凇
 * @moduleName UserEntity
 * @date 2023/1/8 下午7:01
 * @do 用户实体类
 */

@Data
@TableName(value = "user")
public class UserEntity {

    private Long id;

    private String userName;

    private Long age;
}
