package com.miemiemie.starter.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.miemiemie.core.enums.ResultStatusEnum;
import com.miemiemie.core.exception.BizException;
import com.miemiemie.starter.aliyunoss.config.AliyunOssConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * oss 操作帮助类
 *
 * @author yangshunxiang
 * @since 2022/12/16
 */
@Component
@Slf4j
public class AliyunOssTemplate {

    @Resource
    private OSS ossClient;

    @Resource
    private AliyunOssConfig aliyunOssConfig;

    /**
     * @param bucketName      bucket
     * @param fileInputStream 文件流
     * @param objectKey       文件key(文件路径名称)
     * @return 上传后的内网url
     */
    public String upload(String bucketName, InputStream fileInputStream, String objectKey) {
        try {
            ossClient.putObject(bucketName, objectKey, fileInputStream);
            return "https://" + bucketName + "." + aliyunOssConfig.getEndpoint() + "/" + objectKey;
        } catch (OSSException ossException) {
            log.error("上传文件到oss失败", ossException);
            throw new BizException(ResultStatusEnum.FILE_UPLOAD_ERROR.getCode(), "文件上传失败" + ossException.getMessage());
        }
    }


}
