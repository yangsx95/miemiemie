package com.miemiemie.starter.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
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
public class WebAutoConfiguration implements WebMvcConfigurer {

    // 如果目标controller的返回值为string类型，ResultPackageHandler（ResponseAdvice）在包装返回对象时会发生类型转换异常
    // 这是因为StringHttpMessageConverter的优先级要比Object类型的HttpMessageConverter（MappingJackson2HttpMessageConverter）更高
    // 当ResponseAdvice将string类型包装为对象后，再使用StringHttpMessageConverter转换，就会引发对象无法转换string的错误
    // 提高对象转换的优先级，遇到string类型先让对象转换处理
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

}