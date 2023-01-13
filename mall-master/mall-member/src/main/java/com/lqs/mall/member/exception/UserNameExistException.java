package com.lqs.mall.member.exception;

/**
 * @author 李奇凇
 * @moduleName UserNameExistException
 * @date 2022/10/21 下午1:30
 * @do 注册用户名已存在异常
 */
public class UserNameExistException extends RuntimeException{

    public UserNameExistException() {
        super("用户名已经存在");
    }

}
