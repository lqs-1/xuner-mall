package com.lqs.mall.thirdpart.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 李奇凇
 * @date 2022年08月02日 下午4:27
 * @do 自己封装的oss的文件上传工具类
 */
public class OssUploadUtils {

    /**
     *
     * @param type  上传到那个文件夹
     * @param originFileName  原文件的路径
     * @param inputStream  原文件的输入流
     * @param bucketName  Oss的Bucket名字
     * @param ossBaseUrl 外网访问的域名，url
     * @param ossClient   oss客户端
     * @return R
     */

    public static R uploadByFile(String type, String originFileName, InputStream inputStream, OSS ossClient, String ossBaseUrl, String bucketName){
        // 根据种类定义文件夹
        String fileDir = type + File.separator + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + File.separator;
        // 获取原文件的拓展名
        String exName = originFileName.substring(originFileName.lastIndexOf("."));
        // 生成uuid唯一文件主体名字
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 真实的上传文件的路径+文件名，不加域名
        String realFileName = fileDir + fileMainName + exName;

        // 上传文件
        try{
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, realFileName, inputStream);
            ResponseMessage response = putObjectResult.getResponse();
            if(response == null){
                // 上传成功
                String fullUrl = ossBaseUrl + realFileName;

                return R.ok(REnum.UPLOAD_FILE_SUCCESS.getStatusCode(),
                        REnum.UPLOAD_FILE_SUCCESS.getStatusMsg())
                        .put("fullUrl", fullUrl);
            }else {

                return R.error(REnum.UPLOAD_FILE_FAIL.getStatusCode(),
                        REnum.UPLOAD_FILE_FAIL.getStatusMsg());
            }

        }catch (Exception e){
            e.printStackTrace();
            return R.error(REnum.UPLOAD_FILE_EXCEPTION.getStatusCode(),
                    REnum.UPLOAD_FILE_EXCEPTION.getStatusMsg());
        }finally {
//            ossClient.shutdown();
        }
    }



}
