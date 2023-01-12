package com.miemiemie.starter.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.miemiemie.core.enums.ResultStatusEnum;
import com.miemiemie.core.exception.BizException;
import com.miemiemie.core.util.SpringContextHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * 阿里云oss文件对象
 *
 * @author yangshunxiang
 * @since 2023/1/12
 */
@Slf4j
@Data
public class AliyunOssFile {

    private final AliyunOssProperties aliyunOssProperties;

    public AliyunOssFile() {
        aliyunOssProperties = SpringContextHolder.getBean(AliyunOssProperties.class);
        Assert.notNull(aliyunOssProperties, "阿里云oss配置不存在");
    }

    private String bucketName;

    private String baseDir;

    private String dir = "";

    private String filename;

    public String getBucketName() {
        return Optional.ofNullable(bucketName)
                .map(String::trim)
                .orElse(aliyunOssProperties.getDefaultBucketName());
    }

    public String getBaseDir() {
        return Optional.ofNullable(baseDir)
                .map(String::trim)
                .orElse(aliyunOssProperties.getDefaultBaseDir());
    }

    public String getObjectKey() {
        return Paths.get(getBaseDir(), getDir(), getFilename()).toString();
    }

    public String url() {
        return "https://" + getBucketName() + "." + aliyunOssProperties.getEndpoint() + "/" + getObjectKey();
    }

    public String internalUrl() {
        String url = url();
        if (url.contains("internal")) {
            return url;
        } else {
            return url.replace(".aliyuncs.com", "-internal.aliyuncs.com");
        }
    }

    public String openUrl() {
        String url = url();
        if (url.contains("internal")) {
            return url.replace("-internal.aliyuncs.com", ".aliyuncs.com");
        } else {
            return url;
        }
    }

    /**
     * 获取oss客户端
     *
     * @return OSSClient oss客户端
     */
    private @NonNull OSS getOSSClient() {
        OSS ossClient = SpringContextHolder.getBean(OSS.class);
        Assert.notNull(ossClient, "未获取到OSSClient实例");
        return ossClient;
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件流
     */
    public void upload(InputStream inputStream) {
        OSS ossClient = getOSSClient();
        try {
            ossClient.putObject(getBucketName(), getObjectKey(), inputStream);
        } catch (Exception e) {
            log.error("上传文件到oss失败", e);
            throw new BizException(ResultStatusEnum.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 下载oss文件
     *
     * @return InputStream    文件流
     */
    public InputStream download() {
        OSS ossClient = getOSSClient();
        InputStream content;
        try {
            OSSObject ossObj = ossClient.getObject(getBucketName(), getObjectKey());
            content = ossObj.getObjectContent();
        } catch (Exception e) {
            log.error("下载oss文件失败", e);
            throw new BizException(ResultStatusEnum.FILE_DOWNLOAD_ERROR);
        }
        return content;
    }

    /**
     * 删除文件
     */
    public void deleteFile() {
        try {
            getOSSClient().deleteObject(getBucketName(), getObjectKey());
        } catch (Exception e) {
            log.error("删除oss文件失败", e);
        }
    }

}
