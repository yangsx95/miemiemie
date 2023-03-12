package com.miemiemie.starter.data.protection;

import com.miemiemie.starter.data.protection.support.mybatis.MybatisDataProtectionInterceptor;
import com.miemiemie.starter.data.protection.support.web.DataProtectionResponseBodyAdvice;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
