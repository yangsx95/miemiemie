package com.miemiemie.starter.validation;

import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * spring-validation自动配置
 *
 * @author yangshunxiang
 * @since 2023/1/13
 */
@Configuration
public class ValidationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LocalValidatorFactoryBean.class)
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        // 设置spring validator校验快速失败
        factoryBean.getValidationPropertyMap().put(BaseHibernateValidatorConfiguration.FAIL_FAST, Boolean.TRUE.toString());
        return factoryBean;
    }

}
