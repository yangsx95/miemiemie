package com.miemiemie.starter.desensitization;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * easyexcel扩展自动配置
 *
 * @author yangshunxiang
 * @since 2022/12/16
 */
@Configuration
@ComponentScan(basePackageClasses = DesensitizationAutoConfig.class)
@EnableConfigurationProperties
public class DesensitizationAutoConfig {

}
