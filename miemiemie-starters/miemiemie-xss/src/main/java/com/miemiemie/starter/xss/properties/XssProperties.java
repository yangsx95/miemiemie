package com.miemiemie.starter.xss.properties;

import lombok.Data;
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
     */
    private boolean enabled = true;

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


}
