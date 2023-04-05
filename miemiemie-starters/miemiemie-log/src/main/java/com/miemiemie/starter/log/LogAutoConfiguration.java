package com.miemiemie.starter.log;

import com.miemiemie.starter.log.aspect.ControllerLogAspect;
import com.miemiemie.starter.log.aspect.MethodLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangshunxiang
 * @since 2023/4/5
 */
@Configuration
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {

    @ConditionalOnProperty(prefix = LogProperties.MIEMIEMIE_LOG, name = "method.enable", havingValue = "true")
    @Bean
    public MethodLogAspect methodLogAspect() {
        return new MethodLogAspect();
    }

    @ConditionalOnWebApplication
    @ConditionalOnProperty(prefix = LogProperties.MIEMIEMIE_LOG, name = "controller.enable", havingValue = "true")
    @Bean
    public ControllerLogAspect logAspect() {
        return new ControllerLogAspect();
    }

}
