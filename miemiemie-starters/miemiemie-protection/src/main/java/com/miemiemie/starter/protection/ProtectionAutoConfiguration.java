package com.miemiemie.starter.protection;

import com.miemiemie.starter.protection.encryption.Md5Encryptor;
import com.miemiemie.starter.protection.masking.IdNoMasking;
import com.miemiemie.starter.protection.masking.MobileMasking;
import com.miemiemie.starter.protection.support.mybatis.MybatisDataProtectionInterceptor;
import com.miemiemie.starter.protection.support.web.DataProtectionResponseBodyAdvice;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangshunxiang
 * @since 2023/3/12
 */
@Configuration
@EnableConfigurationProperties(ProtectionProperties.class)
public class ProtectionAutoConfiguration {

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
    @ConditionalOnProperty(name = ProtectionProperties.PROPERTIES_PREFIX_ENABLE, havingValue = "true")
    public IdNoMasking idCardProtectionStrategy() {
        return new IdNoMasking();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = ProtectionProperties.PROPERTIES_PREFIX_ENABLE, havingValue = "true")
    public MobileMasking mobileProtectionStrategy() {
        return new MobileMasking();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = ProtectionProperties.PROPERTIES_PREFIX_ENABLE, havingValue = "true")
    public Md5Encryptor md5ProtectionStrategy() {
        return new Md5Encryptor();
    }
}
