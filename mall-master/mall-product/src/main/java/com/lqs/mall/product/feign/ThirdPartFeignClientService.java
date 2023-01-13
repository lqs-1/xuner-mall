package com.lqs.mall.product.feign;

import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 李奇凇
 * @date 2022年08月02日 下午5:02
 * @do 调用storage服务的api
 */

@FeignClient("mall-third-part")
public interface ThirdPartFeignClientService {
    /**
     * 1、向这种传输文件
     * 2、No serializer found for class java.io.FileDescriptor and no properties discovered to create BeanSerializer这个报错的
     * 都用consumes = MediaType.MULTIPART_FORM_DATA_VALUE，标明类型，只是在这个接口上标注
     * @param file
     * @return
     * @throws IOException
     */


    @PostMapping(value = "storage/product/brandLogoUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R brandLogoUpload(@RequestBody MultipartFile file) throws IOException;



    @PostMapping(value = "storage/product/attrGroupIconUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R attrGroupIconUpload(@RequestBody MultipartFile file) throws IOException;


    @PostMapping(value = "storage/product/attrIconUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R attrIconUpload(@RequestBody MultipartFile file) throws IOException;


    @PostMapping(value = "storage/product/spuInfoDescUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R spuInfoDescUpload(@RequestBody MultipartFile file) throws IOException;

    @PostMapping(value = "storage/product/spuImagesUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R spuImagesUpload(@RequestBody MultipartFile file) throws IOException;
}
