package com.lqs.mall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.lqs.mall.auth.feign.MemberFeignClientService;
import com.lqs.mall.auth.feign.ThirdPartFeignClientService;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.to.auth.AccountLoginTo;
import com.lqs.mall.common.to.auth.AccountRegisterTo;
import com.lqs.mall.common.to.auth.AccountRespTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.SmsCodeGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李奇凇
 * @moduleName CommonRegisterAndLoginAccountController
 * @date 2022/10/19 上午10:30
 * @do 普通方式登录和注册方式的controller
 */

@Controller
public class CommonRegisterAndLoginAccountController {

    @Autowired
    private ThirdPartFeignClientService thirdPartFeignClientService;

    @Autowired
    private MemberFeignClientService memberFeignClientService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送验证码
     * @param mobileCode
     * @return 5
     */
    @GetMapping("/sms/sendSmsCode")
    @ResponseBody
    public R sendSmsCode(@RequestParam("mobileCode") String mobileCode){

        Jedis jedis = jedisPool.getResource();

        String realSmsCode = jedis.get(Constant.REGISTER_SMS_PREFIX + mobileCode);


        if (realSmsCode == null || (System.currentTimeMillis() - new Long(realSmsCode.split("_")[1])) / 1000 > 60){

            jedis.del(Constant.REGISTER_SMS_PREFIX + mobileCode);

            String smsCode = SmsCodeGenerateUtils.generateLongSmsCode();

            R result = thirdPartFeignClientService.sendSmsCode(mobileCode, smsCode);

            Integer statusCode = result.parseCode();

            if (statusCode >= 10000 && statusCode < 20000){

                jedis.setex(Constant.REGISTER_SMS_PREFIX + mobileCode, Constant.REGISTER_SMSCODE_INVALID_TIME, smsCode + "_" + System.currentTimeMillis());
            }

            return result;
        }
        return R.error(REnum.SMS_SENDCODE_FAST.getStatusCode(),
                REnum.SMS_SENDCODE_FAST.getStatusMsg());
    }


    /**
     * 以前在product服务中的BrandEntity中定义的数据校验，在处理时候用的是统一异常处理，这里为了方便就不做统一异常处理了，
     * 统一异常处理的方法：
     *  @RestControllerAdvice(basePackages = "com.lqs.mall.product.app"):类上
     *  @ExceptionHandler(value = MethodArgumentNotValidException.class):方法上，表示每一个异常的处理方案
     * @param accountRegisterTo
     * @param bindingResult
     * @return
     */
    @PostMapping("account/commonAccountRegister")
    public String commonAccountRegister(@Valid AccountRegisterTo accountRegisterTo, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        Map<String, String> errorMap = new HashMap<>();

        if (bindingResult.getFieldErrors().size() > 0){

            errorMap = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            redirectAttributes.addFlashAttribute("errors", errorMap);

            return "redirect:http://auth.mall.com/register";
        }

        Jedis jedis = jedisPool.getResource();

        // 验证码是否正确
        String realSmsCode = jedis.get(Constant.REGISTER_SMS_PREFIX + accountRegisterTo.getMobileCode());

        if (realSmsCode != null && accountRegisterTo.getSmsCode().equalsIgnoreCase(realSmsCode.split("_")[0])){

            jedis.del(Constant.REGISTER_SMS_PREFIX + accountRegisterTo.getMobileCode());

            // 调用远程服务进行注册
            R registerResponse = memberFeignClientService.commonAccountRegister(accountRegisterTo);

            if (registerResponse.parseCode() >= 10000 && registerResponse.parseCode() < 20000){
                return "redirect:http://auth.mall.com/login";
            }

            errorMap.put(registerResponse.parseType(new TypeReference<String>(){}, "errorKey"), registerResponse.parseMsg());

            redirectAttributes.addFlashAttribute("errors", errorMap);

            return "redirect:http://auth.mall.com/register";

        }

        errorMap.put("smsCode", "验证码错误");

        redirectAttributes.addFlashAttribute("errors", errorMap);

        return "redirect:http://auth.mall.com/register";

    }


    @PostMapping("account/commonAccountLogin")
    public String commonAccountLogin(@Valid AccountLoginTo accountLoginTo, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session){

        // System.out.println(accountLoginTo);

        List<String> errorsList = new ArrayList<>();

        if (bindingResult.getFieldErrors().size() > 0){

            errorsList = bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());

            redirectAttributes.addFlashAttribute("errors", errorsList);

            return "redirect:http://auth.mall.com/login";
        }

        // 远程登录
        R loginResponse = memberFeignClientService.commonAccountLogin(accountLoginTo);

        if (loginResponse.parseCode() >= 10000 && loginResponse.parseCode() < 20000){
            session.setAttribute(Constant.ACCOUNT_SESSION_KEY, loginResponse.parseType(new TypeReference<AccountRespTo>(){}, "account"));

            session.setMaxInactiveInterval(60*60*24); // 设置过期时间，单位秒，0或者负数为不过期

            return "redirect:http://www.mall.com";
        }

        errorsList.add(loginResponse.parseMsg());
        redirectAttributes.addFlashAttribute("errors", errorsList);

        return "redirect:http://auth.mall.com/login";

    }



}
