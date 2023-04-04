package com.miemiemie.starter.xss;

import com.miemiemie.starter.xss.filter.XssFilter;
import com.miemiemie.starter.xss.ignore.SpringPropertiesXssRequestIgnorer;
import com.miemiemie.starter.xss.ignore.XssRequestIgnorer;
import com.miemiemie.starter.xss.properties.XssProperties;
import org.owasp.validator.html.Policy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.miemiemie.starter.xss.properties.XssProperties.MIEMIEMIE_XSS;

/**
 * @author yangshunxiang
 * @since 2023/4/2
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(XssProperties.class)
public class XssAutoConfiguration {

    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = MIEMIEMIE_XSS, name = "enabled", havingValue = "true")
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter(List<XssRequestIgnorer> ignorers, Policy xssPolicy) {
        FilterRegistrationBean<XssFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new XssFilter(ignorers, xssPolicy));
        filter.addUrlPatterns("/**");
        filter.setName("xssFilter");
        filter.setOrder(1);
        return filter;
    }

    @Bean
    public SpringPropertiesXssRequestIgnorer springPropertiesXssRequestIgnorer(XssProperties xssProperties) {
        return new SpringPropertiesXssRequestIgnorer(xssProperties);
    }
}
