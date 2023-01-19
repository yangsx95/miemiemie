package com.miemiemie.starter.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author yangshunxiang
 * @since 2023/1/18
 */
@SpringBootApplication
@EnableWebMvc
public class SwaggerWebApp {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerWebApp.class, args);
    }

}
