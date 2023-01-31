package com.miemiemie.starter.aliyunoss;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云oss自动配置
 *
 * @author yangshunxiang
 * @since 2022/12/16
 */
@Configuration
@EnableConfigurationProperties(AliyunOssProperties.class)
@ComponentScan(basePackageClasses = AliyunOssAutoConfig.class)
public class AliyunOssAutoConfig {

    private final AliyunOssProperties aliyunOssProperties;

    public AliyunOssAutoConfig(final AliyunOssProperties aliyunOssProperties) {
        this.aliyunOssProperties = aliyunOssProperties;
    }

    @Bean
    public AliyunOssClientFactoryBean ossClientFactoryBean() {
        final AliyunOssClientFactoryBean factoryBean = new AliyunOssClientFactoryBean();
        factoryBean.setEndpoint(this.aliyunOssProperties.getEndpoint());
        factoryBean.setAccessKeyId(this.aliyunOssProperties.getAccessKeyId());
        factoryBean.setAccessKeySecret(this.aliyunOssProperties.getAccessKeySecret());
        return factoryBean;
    }
}
