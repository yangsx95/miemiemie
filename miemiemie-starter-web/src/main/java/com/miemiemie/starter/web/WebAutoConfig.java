package com.miemiemie.starter.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackageClasses = WebAutoConfig.class)
@EnableWebMvc
public class WebAutoConfig {
}
