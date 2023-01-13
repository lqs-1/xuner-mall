package com.lqs.mall.thirdpart.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author 李奇凇
 * @moduleName OssSmsUtils
 * @date 2022/10/19 下午5:24
 * @do Aliyun短信服务工具类
 */
@ConfigurationProperties(prefix = "spring.cloud.alicloud")
@Data
@Component
public class AliyunSmsUtils {

    private String accessKey;
    private String secretKey;
    private String region;

    private String templateCode;


    /**
     * 发送验证码方法
     * @param mobileCode 接收验证码的手机号
     * @param smsCode 要发送的验证码
     * @return
     */

    public R sendSmsCode(String mobileCode, String smsCode){
        // 构建凭证
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKey)
                .accessKeySecret(secretKey)
                .build());

        // 配置短信发送客户端
        AsyncClient client = AsyncClient.builder()
                .region(region) // 区域编号
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();

        // 构建数据对象
        Map<String, String> originCode = new HashMap<>();
        originCode.put("code", smsCode);
        String codeEncode = JSON.toJSONString(originCode);


        // 构建Api请求参数
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("阿里云短信测试")
                .templateCode(templateCode)
                .phoneNumbers(mobileCode)
                .templateParam(codeEncode)
                .build();

        try{
            // 程序代码
            // 异步发送短信
            CompletableFuture<SendSmsResponse> responseObject = client.sendSms(sendSmsRequest);
            // 等待短信发送完成的结果（阻塞）
            SendSmsResponse response = responseObject.get();
            System.out.println(response.getBody().getMessage());
            if (response.getBody().getMessage().equals("OK")){
                return R.ok(REnum.SMS_SENDCODE_SUCCESS.getStatusCode(),
                        REnum.SMS_SENDCODE_SUCCESS.getStatusMsg());
            }

            return R.error(REnum.SMS_SENDCODE_FAIL.getStatusCode(),
                    REnum.SMS_SENDCODE_FAIL.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.SMS_SENDCODE_FAIL.getStatusCode(),
                    REnum.SMS_SENDCODE_FAIL.getStatusMsg());

        }finally{
            // 程序代码
            client.close();
        }

    }


}
