package com.miemiemie.starter.data.protection;

import com.miemiemie.starter.data.protection.strategy.encryption.Md5EncryptionStrategy;
import com.miemiemie.starter.data.protection.strategy.protection.IdCardProtectionStrategy;
import com.miemiemie.starter.data.protection.strategy.protection.MobileProtectionStrategy;
import com.miemiemie.starter.data.protection.support.mybatis.MybatisDataProtectionInterceptor;
import com.miemiemie.starter.data.protection.support.web.DataProtectionResponseBodyAdvice;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.miemiemie.starter.data.protection.DataProtectionProperties.PROPERTIES_PREFIX_ENABLE;

/**
 * @author yangshunxiang
 * @since 2023/3/12
 */
@Configuration
@EnableConfigurationProperties(DataProtectionProperties.class)
public class DataProtectionAutoConfiguration {

    @Bean
    @ConditionalOnClass(Interceptor.class)
    @ConditionalOnMissingBean
    public MybatisDataProtectionInterceptor mybatisDataProtectionInterceptor() {
        return new MybatisDataProtectionInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public DataProtectionResponseBodyAdvice dataProtectionResponseBodyAdvice() {
        return new DataProtectionResponseBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = PROPERTIES_PREFIX_ENABLE, havingValue = "true")
    public IdCardProtectionStrategy idCardProtectionStrategy() {
        return new IdCardProtectionStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = PROPERTIES_PREFIX_ENABLE, havingValue = "true")
    public MobileProtectionStrategy mobileProtectionStrategy() {
        return new MobileProtectionStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = PROPERTIES_PREFIX_ENABLE, havingValue = "true")
    public Md5EncryptionStrategy md5ProtectionStrategy() {
        return new Md5EncryptionStrategy();
    }
}
