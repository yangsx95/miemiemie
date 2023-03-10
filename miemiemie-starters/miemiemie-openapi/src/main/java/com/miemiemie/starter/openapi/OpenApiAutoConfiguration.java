package com.miemiemie.starter.openapi;

import cn.hutool.core.collection.CollUtil;
import com.miemiemie.starter.openapi.plugin.CommonEnumModelPropertyBuilderPlugin;
import com.miemiemie.starter.openapi.plugin.CommonEnumParameterPlugin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

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
@Import({OpenApiWebMvcConfig.class, CommonEnumParameterPlugin.class, CommonEnumModelPropertyBuilderPlugin.class})
public class OpenApiAutoConfiguration implements BeanFactoryAware {

    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public List<OpenApiProperties.DocketInfo> createDockets(OpenApiProperties properties) {

        List<OpenApiProperties.DocketInfo> docketInfoList = properties.getDocketInfoList();
        if (CollUtil.isEmpty(docketInfoList)) {
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }

}
