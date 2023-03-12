package com.miemiemie.starter.openapi;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Swagger自动配置
 *
 * @author yangshunxiang
 * @since 2023/1/18
 */
@ConditionalOnWebApplication
@Configuration
@Import({CommonEnumPropertyCustomizer.class, CommonEnumParameterCustomizer.class})
public class OpenApiAutoConfiguration {

}
