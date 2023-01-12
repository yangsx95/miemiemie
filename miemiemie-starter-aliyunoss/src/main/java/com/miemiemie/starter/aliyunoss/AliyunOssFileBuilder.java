package com.miemiemie.starter.aliyunoss;

import com.miemiemie.core.exception.BizException;
import com.miemiemie.core.util.SpringContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * 阿里云oss文件对象建造器
 * @author yangshunxiang
 * @since 2023/1/12
 */
public final class AliyunOssFileBuilder {

    private final AliyunOssFile aliyunOssFile = new AliyunOssFile();

    public static AliyunOssFileBuilder builder() {
        return new AliyunOssFileBuilder();
    }

    public AliyunOssFileBuilder endpoint(@NonNull String endpoint) {
        this.aliyunOssFile.setEndpoint(endpoint);
        return this;
    }

    public AliyunOssFileBuilder bucketName(@NonNull String bucketName) {
        this.aliyunOssFile.setBucket(bucketName);
        return this;
    }

    public AliyunOssFileBuilder dir(@NonNull String dir) {
        this.aliyunOssFile.setDir(dir);
        return this;
    }

    public AliyunOssFileBuilder filename(@NonNull String filename) {
        this.aliyunOssFile.setFilename(filename);
        return this;
    }

    public AliyunOssFileBuilder file(@NonNull File file) {
        this.aliyunOssFile.setFilename(file.getName());
        return this;
    }

    public AliyunOssFile build() {
        if (!StringUtils.hasText(aliyunOssFile.getFilename())) {
            throw new BizException("文件名称不存在");
        }
        return aliyunOssFile;
    }

}
