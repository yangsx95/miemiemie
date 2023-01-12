package com.miemiemie.starter.aliyunoss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * oss 配置
 *
 * @author yangshunxiang
 * @since 2022/12/16
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "oss")
public class AliyunOssProperties {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String defaultBucket;

    private String defaultDir;

}
