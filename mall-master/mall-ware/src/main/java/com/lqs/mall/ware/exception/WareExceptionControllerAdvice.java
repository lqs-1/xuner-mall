package com.lqs.mall.ware.exception;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName WareExceptionControllerAdvice
 * @date 2022/9/24 上午10:06
 * @do 集中处理所有异常
 */

// 使用@RestControllerAdvice注解,指定统一处理哪里的异常
//@RestControllerAdvice(basePackages = "com.lqs.mall.ware.app")
// 记录日志
@Slf4j
public class WareExceptionControllerAdvice {
    /**
     * 数据校验统一异常处理
     * @param validException
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException validException){
        // 获取异常集合
        BindingResult bindingResult = validException.getBindingResult();
        // 获取字段异常列表
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        // 收集字段异常的集合
        HashMap<String, String> fieldValidMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            // 获取异常字段的名称
            String field = fieldError.getField();
            // 获取异常消息
            String defaultMessage = fieldError.getDefaultMessage();
            fieldValidMap.put(field, defaultMessage);
        }

        return R.error(REnum.VALID_DATA_FAIL.getStatusCode(),
                REnum.VALID_DATA_FAIL.getStatusMsg()).put("error", fieldValidMap);
    }
}
