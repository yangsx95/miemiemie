package com.miemiemie.starter.web.config;

import com.miemiemie.starter.web.convert.CommonEnumConvertFactory;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;

/**
 * web自动配置
 *
 * @author 杨顺翔
 * @since 2022/07/30
 */
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private CommonEnumConvertFactory commonEnumConvertFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(commonEnumConvertFactory);
    }
}