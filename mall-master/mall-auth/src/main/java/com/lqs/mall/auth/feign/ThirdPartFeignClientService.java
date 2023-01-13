package com.lqs.mall.auth.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 李奇凇
 * @date 2022年08月02日 下午5:02
 * @do 调用third-part服务的api
 */

@FeignClient("mall-third-part")
public interface ThirdPartFeignClientService {
    /**
     * 1、向这种传输文件
     * 2、No serializer found for class java.io.FileDescriptor and no properties discovered to create BeanSerializer这个报错的
     * 都用consumes = MediaType.MULTIPART_FORM_DATA_VALUE，标明类型，只是在这个接口上标注
     */
    @GetMapping("sms/sendSmsCode")
    public R sendSmsCode(@RequestParam("mobileCode") String mobileCode, @RequestParam("smsCode") String smsCode);
}
