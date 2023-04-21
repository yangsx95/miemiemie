package com.miemiemie.starter.xss.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangshunxiang
 * @since 2023/4/2
 */
@Data
@ConfigurationProperties(value = XssProperties.MIEMIEMIE_XSS)
public class XssProperties {

    public static final String MIEMIEMIE_XSS = "miemiemie.xss";

    /**
     * 是否开启XSS过滤
     * 备注：
     * 使用 @Value 注解可以将属性的默认值外部化到配置文件中，例如 application.properties 或 application.yml 文件中，从而提高代码的可配置性和可重用性。
     * 如果使用字段初始值，配置文件中的 miemiemie.xss 仍然是空，那么ConditionOnProperty注解的条件判断始终不会通过，这是一个坑
     */
    @Value("true")
    private boolean enabled;

    /**
     * 排除的URL，支持ant表达式
     */
    private List<String> excludeUrlPath = new ArrayList<>();

    /**
     * 排除的特殊值
     */
    private List<String> excludeValue = new ArrayList<>();

    /**
     * 排除的请求参数名称
     */
    private List<String> excludeParameterName = new ArrayList<>();

    /**
     * 排除的请求头名称
     */
    private List<String> excludeHeaderName = new ArrayList<>();

    /**
     * 排除的请求参数值
     */
    private List<String> excludeParameterValue = new ArrayList<>();

    /**
     * 排除的请求头值
     */
    private List<String> excludeHeaderValue = new ArrayList<>();

    /**
     * 策略文件路径
     */
    @Value("classpath:/antisamy.xml")
    private String policyFilePath;

}
