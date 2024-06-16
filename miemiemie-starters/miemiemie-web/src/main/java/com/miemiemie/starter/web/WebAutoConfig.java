package com.miemiemie.starter.web;

import com.miemiemie.starter.web.properties.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackageClasses = WebAutoConfig.class)
@EnableWebMvc
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfig {
}
