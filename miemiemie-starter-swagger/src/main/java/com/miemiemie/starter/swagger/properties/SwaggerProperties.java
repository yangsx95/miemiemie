package com.miemiemie.starter.swagger.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * Swagger配置对象
 *
 * @author yangshunxiang
 * @since 2023/1/18
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = SwaggerProperties.MIEMIEMIE_SWAGGER_PROPERTIES_PREFIX)
public class SwaggerProperties {

    public static final String MIEMIEMIE_SWAGGER_PROPERTIES_PREFIX = "miemiemie.swagger";

    /**
     * Docket列表，可多个
     */
    private List<DocketProperties> dockets;

}
