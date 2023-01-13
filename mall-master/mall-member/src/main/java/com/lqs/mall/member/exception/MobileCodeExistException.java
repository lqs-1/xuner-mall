package com.lqs.mall.member.exception;

/**
 * @author 李奇凇
 * @moduleName MobileCodeExistException
 * @date 2022/10/21 下午1:30
 * @do 注册手机号已存在异常
 */
public class MobileCodeExistException extends RuntimeException{

    public MobileCodeExistException() {
        super("手机号已经存在");
    }

}
