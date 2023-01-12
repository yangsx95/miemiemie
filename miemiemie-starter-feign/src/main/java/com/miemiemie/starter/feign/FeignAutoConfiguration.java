package com.miemiemie.starter.feign;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Configuration
@ComponentScan(basePackageClasses = FeignAutoConfiguration.class)
public class FeignAutoConfiguration {
}
