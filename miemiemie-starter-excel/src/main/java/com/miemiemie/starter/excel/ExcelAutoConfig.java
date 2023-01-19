package com.miemiemie.starter.excel;

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
@ComponentScan(basePackageClasses = ExcelAutoConfig.class)
@EnableConfigurationProperties
public class ExcelAutoConfig {

}
