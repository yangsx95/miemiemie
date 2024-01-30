package com.miemiemie.starter.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Configuration
public class FeignAutoConfiguration {

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public ResultDecoder resultDecoder(ObjectMapper objectMapper) {
        return new ResultDecoder(objectMapper);
    }

    @Bean
    public JsonErrorDecoder errorDecoder() {
        return new JsonErrorDecoder();
    }

    @Bean
    public FeignLogger feignLogger() {
        return new FeignLogger();
    }

}
