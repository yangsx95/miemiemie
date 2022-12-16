package com.miemiemie.starter.aliyunoss;

import com.miemiemie.starter.aliyunoss.config.AliyunOssConfig;
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
@ComponentScan(basePackageClasses = AliyunOssAutoConfig.class)
public class AliyunOssAutoConfig {

    private final AliyunOssConfig aliyunOssConfig;

    public AliyunOssAutoConfig(final AliyunOssConfig aliyunOssConfig) {
        this.aliyunOssConfig = aliyunOssConfig;
    }

    @Bean
    public AliyunOssClientFactoryBean ossClientFactoryBean() {
        final AliyunOssClientFactoryBean factoryBean = new AliyunOssClientFactoryBean();
        factoryBean.setEndpoint(this.aliyunOssConfig.getEndpoint());
        factoryBean.setAccessKeyId(this.aliyunOssConfig.getAccessKeyId());
        factoryBean.setAccessKeySecret(this.aliyunOssConfig.getAccessKeySecret());
        return factoryBean;
    }
}
