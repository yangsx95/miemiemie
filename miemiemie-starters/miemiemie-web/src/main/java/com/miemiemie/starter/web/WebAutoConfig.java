package com.miemiemie.starter.web;

import com.miemiemie.starter.web.properties.WebProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@AutoConfiguration
@ComponentScan(basePackageClasses = WebAutoConfig.class)
@EnableWebMvc
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfig {
}
