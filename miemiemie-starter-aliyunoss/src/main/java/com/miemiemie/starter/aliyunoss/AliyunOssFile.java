package com.miemiemie.starter.aliyunoss;

import com.miemiemie.core.util.SpringContextHolder;
import lombok.Data;
import org.springframework.util.Assert;

import java.nio.file.Paths;
import java.util.Optional;

/**
 * 阿里云文件操作工具
 *
 * @author yangshunxiang
 * @since  2023/1/12
 */
@Data
public class AliyunOssFile {

    private final AliyunOssProperties aliyunOssProperties;

    public AliyunOssFile() {
        aliyunOssProperties = SpringContextHolder.getBean(AliyunOssProperties.class);
        Assert.notNull(aliyunOssProperties, "阿里云oss配置不存在");
    }

    private String endpoint;

    private String bucket;

    private String dir;

    private String filename;

    public String getEndpoint() {
        return Optional.ofNullable(endpoint)
                .map(String::trim)
                .orElse(aliyunOssProperties.getEndpoint());
    }

    public String getBucket() {
        return Optional.ofNullable(bucket)
                .map(String::trim)
                .orElse(aliyunOssProperties.getDefaultBucket());
    }

    public String getDir() {
        return Optional.ofNullable(dir)
                .map(String::trim)
                .orElse(aliyunOssProperties.getDefaultDir());
    }

    public String getObjectKey() {
        return Paths.get(dir, filename).toString();
    }

    public String url() {
        return "https://" + getBucket() + "." + getEndpoint() + "/" + getObjectKey();
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
}
