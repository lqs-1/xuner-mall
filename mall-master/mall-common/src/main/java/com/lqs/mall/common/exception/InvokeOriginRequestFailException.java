package com.lqs.mall.common.exception;

/**
 * @author 李奇凇
 * @moduleName 模块名字
 * @date 2022/10/28 下午3:30
 * @do 一句话描述该模块的功能
 */
public class InvokeOriginRequestFailException extends RuntimeException {


    public InvokeOriginRequestFailException() {
        super("远程调用异常");
    }
}
