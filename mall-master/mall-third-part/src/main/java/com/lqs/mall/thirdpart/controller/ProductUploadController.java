package com.lqs.mall.thirdpart.controller;

import com.aliyun.oss.OSS;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.thirdpart.utils.OssUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 李奇凇
 * @date 2022年08月02日 下午4:36
 * @do 所有的product的文件存储
 */

@RestController
@RequestMapping("storage/product")
public class ProductUploadController {


    // 外网访问的域名，url
    @Value("${oss.base.url}")
    private String ossBaseUrl;

    // Oss的客户端对象
    @Autowired
    private OSS ossClient;

    @Value("${oss.bucketName}")
    private String bucketName;

    /**
     * brandLogoUpload of product
     * @param file origin file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "brandLogoUpload")
    public R brandLogoUpload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();


//        String contentType = file.getContentType();

//        R brand = minioUploadUtils.uploadFile(originalFilename, inputStream, contentType);

         R brand = OssUploadUtils.uploadByFile("product/brand", originalFilename, inputStream, ossClient, ossBaseUrl, bucketName);
        return brand;
    }


    /**
     * attrGroupLogoUpload of product
     * @param file origin file, type : image
     * @return
     * @throws IOException
     */
    @PostMapping(value = "attrGroupIconUpload")
    public R attrGroupIconUpload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();


//        String contentType = file.getContentType();

//        R brand = minioUploadUtils.uploadFile(originalFilename, inputStream, contentType);

        R attrgroup = OssUploadUtils.uploadByFile("product/attrgroup", originalFilename, inputStream, ossClient, ossBaseUrl, bucketName);
        return attrgroup;
    }


    /**
     * attrIconUpload of product
     * @param file origin file, type : image
     * @return
     * @throws IOException
     */
    @PostMapping(value = "attrIconUpload")
    public R attrIconUpload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();


//        String contentType = file.getContentType();

//        R brand = minioUploadUtils.uploadFile(originalFilename, inputStream, contentType);

        R attr = OssUploadUtils.uploadByFile("product/attr", originalFilename, inputStream, ossClient, ossBaseUrl, bucketName);
        return attr;
    }



    /**
     * spu Desc big Picture Storage
     * @param file origin file, type : image
     * @return
     * @throws IOException
     */
    @PostMapping(value = "spuInfoDescUpload")
    public R spuInfoDescUpload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();


//        String contentType = file.getContentType();

//        R brand = minioUploadUtils.uploadFile(originalFilename, inputStream, contentType);

        R spuInfoDesc = OssUploadUtils.uploadByFile("product/spuInfoDesc", originalFilename, inputStream, ossClient, ossBaseUrl, bucketName);
        return spuInfoDesc;
    }


    /**
     * spu Images Picture Storage
     * @param file origin file, type : image
     * @return
     * @throws IOException
     */
    @PostMapping(value = "spuImagesUpload")
    public R spuImagesUpload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();


//        String contentType = file.getContentType();

//        R brand = minioUploadUtils.uploadFile(originalFilename, inputStream, contentType);

        R spuImages = OssUploadUtils.uploadByFile("product/spuImages", originalFilename, inputStream, ossClient, ossBaseUrl, bucketName);
        return spuImages;
    }


}
