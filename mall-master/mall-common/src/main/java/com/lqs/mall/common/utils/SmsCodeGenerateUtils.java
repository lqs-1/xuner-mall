package com.lqs.mall.common.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Consumer;

/**
 * @author 李奇凇
 * @moduleName SmsCodeGenerateUtils
 * @date 2022/10/19 下午5:43
 * @do 生成验证码的工具类
 */

@Component
public class SmsCodeGenerateUtils {

    /**
     * 生成短验证码，4位
     * @return
     */
    public static String generateShotSmsCode(){
        return generateSmsCode(4);
    }

    /**
     * 生成短验证码，6位
     * @return
     */
    public static String generateLongSmsCode(){
        return generateSmsCode(6);
    }

    /**
     * 生成指定长度的验证码
     * @param smsCodeLen 验证码长度
     * @return
     */
    private static String generateSmsCode(Integer smsCodeLen){
        //定义取值范围
        String str = "0123456789";
        //容量为4
        StringBuilder smsCode = new StringBuilder(smsCodeLen);
        for (int i = 0; i < smsCodeLen; i++) {
            //遍历smsCodeLen次，拿到某个字符并且拼接,nextInt(10)表示每个数的取值范围为[0-10)
            char ch = str.charAt(new Random().nextInt(10));
            smsCode.append(ch);
        }
        return smsCode.toString();
    }


    public static void main(String[] args) {
        SmsCodeGenerateUtils smsCodeGenerateUtils = new SmsCodeGenerateUtils();
        String s = smsCodeGenerateUtils.generateLongSmsCode();
        System.out.println(s);

        String s1 = smsCodeGenerateUtils.generateShotSmsCode();
        System.out.println(s1);

    }




}
