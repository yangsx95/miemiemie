package com.miemiemie.starter.openapi;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

/**
 * Swagger自动配置
 *
 * @author yangshunxiang
 * @since 2023/1/18
 */
@ConditionalOnWebApplication
@AutoConfiguration
@Import({
        CommonEnumPropertyCustomizer.class,
        CommonEnumParameterCustomizer.class,
        PrintOpenApiInfoCommandLineRunner.class
})
public class OpenApiAutoConfiguration {
}
