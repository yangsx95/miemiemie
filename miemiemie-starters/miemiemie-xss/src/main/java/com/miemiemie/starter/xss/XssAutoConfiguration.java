package com.miemiemie.starter.xss;

import com.miemiemie.starter.core.exception.BizException;
import com.miemiemie.starter.xss.filter.XssFilter;
import com.miemiemie.starter.xss.ignore.SpringPropertiesXssRequestIgnorer;
import com.miemiemie.starter.xss.ignore.XssRequestIgnorer;
import com.miemiemie.starter.xss.properties.XssProperties;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

import static com.miemiemie.starter.xss.properties.XssProperties.MIEMIEMIE_XSS;

/**
 * @author yangshunxiang
 * @since 2023/4/2
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(prefix = MIEMIEMIE_XSS, name = "enabled", havingValue = "true")
public class XssAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter(List<XssRequestIgnorer> ignorers, Policy xssPolicy) {
        FilterRegistrationBean<XssFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new XssFilter(ignorers, xssPolicy));
        filter.addUrlPatterns("/*");
        filter.setName("xssFilter");
        filter.setOrder(1);
        return filter;
    }

    @ConditionalOnMissingBean
    @Bean
    public SpringPropertiesXssRequestIgnorer springPropertiesXssRequestIgnorer(XssProperties xssProperties) {
        return new SpringPropertiesXssRequestIgnorer(xssProperties);
    }

    @ConditionalOnMissingBean
    @Bean
    public Policy defaultXssPolicy(ResourceLoader resourceLoader, XssProperties xssProperties) {
        String policyFilePath = xssProperties.getPolicyFilePath();
        try {
            Resource resource = resourceLoader.getResource(policyFilePath);
            return Policy.getInstance(resource.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("读取Antisamy策略文件失败", e);
        }
    }
}
