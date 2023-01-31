package com.miemiemie.starter.aliyunoss;

import com.github.f4b6a3.ulid.Ulid;
import com.miemiemie.core.exception.BizException;
import org.springframework.util.StringUtils;

/**
 * 阿里云oss文件对象建造器
 *
 * @author yangshunxiang
 * @since 2023/1/12
 */
public class AliyunOssFileBuilder {

    private final AliyunOssFile aliyunOssFile = new AliyunOssFile();

    public static AliyunOssFileBuilder builder() {
        return new AliyunOssFileBuilder();
    }

    public AliyunOssFileBuilder bucketName(String bucketName) {
        this.aliyunOssFile.setBucketName(bucketName);
        return this;
    }

    public AliyunOssFileBuilder baseDir(String baseDir) {
        this.aliyunOssFile.setBaseDir(baseDir);
        return this;
    }

    public AliyunOssFileBuilder dir(String dir) {
        this.aliyunOssFile.setDir(dir);
        return this;
    }

    public AliyunOssFileBuilder randomDir() {
        this.aliyunOssFile.setDir(Ulid.fast().toString());
        return this;
    }

    public AliyunOssFileBuilder filename(String filename) {
        this.aliyunOssFile.setFilename(filename);
        return this;
    }

    public AliyunOssFileBuilder randomFilename(String fileExtension) {
        if (!StringUtils.hasText(fileExtension)) {
            this.aliyunOssFile.setFilename(Ulid.fast().toString());
            return this;
        }
        fileExtension = fileExtension.trim().replaceAll("^\\.+", "");
        this.aliyunOssFile.setFilename(Ulid.fast() + "." + fileExtension);
        return this;
    }

    public AliyunOssFile build() {
        if (!StringUtils.hasText(aliyunOssFile.getFilename())) {
            throw new BizException("文件名称不存在");
        }
        return aliyunOssFile;
    }

}
