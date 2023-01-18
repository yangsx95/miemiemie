package com.miemiemie.starter.swagger;

import com.miemiemie.starter.swagger.properties.ApiInfoProperties;
import com.miemiemie.starter.swagger.properties.DocketProperties;
import com.miemiemie.starter.swagger.properties.SwaggerProperties;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Objects;

import static com.miemiemie.starter.swagger.properties.SwaggerProperties.MIEMIEMIE_SWAGGER_PROPERTIES_PREFIX;

/**
 * @author yangshunxiang
 * @since 2023/1/18
 */
public class SwaggerConfigRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private SwaggerProperties swaggerProperties;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        swaggerProperties = environment.getProperty(MIEMIEMIE_SWAGGER_PROPERTIES_PREFIX, SwaggerProperties.class);
    }

    @Override
    public void registerBeanDefinitions(@NonNull  AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
        if (Objects.isNull(swaggerProperties) || CollectionUtils.isEmpty(swaggerProperties.getDockets())) {
            return;
        }

    }

    private Docket buildDocket(DocketProperties docketProperties) {
        return new Docket(docketProperties.getDocType())
                .groupName(docketProperties.getGroupName())
                .apiInfo(buildApiInfo(docketProperties.getApiInfo()))
                .globalRequestParameters(docketProperties.getGlobalRequestParameters());
    }

    private ApiInfo buildApiInfo(ApiInfoProperties apiInfoProperties) {
        return new ApiInfoBuilder().build();
    }
}
