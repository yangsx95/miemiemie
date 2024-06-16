package com.miemiemie.starter.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨顺翔
 * @since 2024/6/16
 */
@Data
@ConfigurationProperties(prefix = "miemiemie.web")
public class WebProperties {

    /**
     * 响应结果包装白名单
     */
    private List<String> resultPackageWhiteList = new ArrayList<>();

}
