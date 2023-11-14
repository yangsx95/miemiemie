package com.miemiemie.starter.log;

import com.miemiemie.starter.log.aspect.ControllerLogAspect;
import com.miemiemie.starter.log.aspect.MethodLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
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
    @ConditionalOnMissingBean
    @Bean
    public MethodLogAspect methodLogAspect(ApplicationContext applicationContext) {
        return new MethodLogAspect(applicationContext);
    }

    @ConditionalOnWebApplication
    @ConditionalOnProperty(prefix = LogProperties.MIEMIEMIE_LOG, name = "controller.enable", havingValue = "true")
    @Bean
    public ControllerLogAspect logAspect(ApplicationContext applicationContext, LogProperties logProperties) {
        return new ControllerLogAspect(applicationContext, logProperties);
    }

}
