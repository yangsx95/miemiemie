package com.miemiemie.starter.oss;

import com.amazonaws.services.s3.model.S3Object;
import com.miemiemie.core.enums.ResultStatusEnum;
import com.miemiemie.core.exception.BizException;
import com.miemiemie.core.util.SpringContextHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;

/**
 * oss文件对象
 *
 * @author yangshunxiang
 * @since 2023/1/12
 */
@Slf4j
@Data
public class OssFile {

    private final OssTemplate ossTemplate;

    public OssFile() {
        ossTemplate = SpringContextHolder.getBean(OssTemplate.class);
        Assert.notNull(ossTemplate, "ossTemplate不存在");

    }

    private String bucketName;

    private String dir = "/";

    private String filename;

    public String getBucketName() {
        return Optional.ofNullable(bucketName)
                .map(String::trim)
                .orElse(ossTemplate.getOssProperties().getDefaultBucketName());
    }

    public String getObjectKey() {
        return Paths.get(getDir(), getFilename()).toString();
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件流
     */
    public void upload(InputStream inputStream) {
        try {
            ossTemplate.putObject(getBucketName(), getObjectKey(), inputStream);
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
        InputStream content;
        try {
            S3Object object = ossTemplate.getObject(getBucketName(), getObjectKey());
            content = object.getObjectContent();
        } catch (Exception e) {
            log.error("下载oss文件失败", e);
            throw new BizException(ResultStatusEnum.FILE_DOWNLOAD_ERROR);
        }
        return content;
    }

    /**
     * 外链
     *
     * @param expires 过期时间
     * @return 外链地址
     */
    public String presignedUrl(Duration expires) {
        return ossTemplate.generatePresignedUrl(getBucketName(), getObjectKey(), expires);
    }

    /**
     * 删除文件
     */
    public void deleteFile() {
        try {
            ossTemplate.deleteObject(getBucketName(), getObjectKey());
        } catch (Exception e) {
            log.error("删除oss文件失败", e);
        }
    }

}
