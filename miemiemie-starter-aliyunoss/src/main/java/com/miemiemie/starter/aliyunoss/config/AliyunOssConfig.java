package com.miemiemie.starter.aliyunoss.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * oss 配置
 *
 * @author yangshunxiang
 * @since 2022/12/16
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "oss")
public class AliyunOssConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

}
