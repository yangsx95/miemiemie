package com.miemiemie.starter.swagger;

import com.miemiemie.starter.swagger.plugin.CommonEnumModelPropertyBuilderPlugin;
import com.miemiemie.starter.swagger.plugin.CommonEnumParameterPlugin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger自动配置
 *
 * @author yangshunxiang
 * @since 2023/1/18
 */
@ConditionalOnWebApplication
@EnableSwagger2
@EnableOpenApi
@EnableConfigurationProperties
@Configuration
@Import({SwaggerWebMvcConfig.class, CommonEnumParameterPlugin.class, CommonEnumModelPropertyBuilderPlugin.class})
public class SwaggerAutoConfiguration {

}
