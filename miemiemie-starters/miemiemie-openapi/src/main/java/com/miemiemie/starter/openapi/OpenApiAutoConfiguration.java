package com.miemiemie.starter.openapi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.miemiemie.starter.openapi.plugin.CommonEnumModelPropertyBuilderPlugin;
import com.miemiemie.starter.openapi.plugin.CommonEnumParameterPlugin;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//import static sun.rmi.transport.proxy.CGIHandler.RequestMethod;


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

    private static final String SEMICOLON = ";";
    private final OpenApiProperties openApiProperties;
    private BeanFactory beanFactory;
    private final OpenApiExtensionResolver openApiExtensionResolver;

    public OpenApiAutoConfiguration(OpenApiProperties OpenApiProperties, OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiProperties = OpenApiProperties;
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    private static Predicate<String> ant(final String antPattern) {
        return input -> new AntPathMatcher().match(antPattern, input);
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> StrUtil.split(basePackage, SEMICOLON).stream().anyMatch(ClassUtils.getPackageName(input)::startsWith);
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

    @Bean
    @ConditionalOnMissingBean
    public List<Docket> createRestApi() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();

        // 没有分组
        if (openApiProperties.getDocket().isEmpty()) {
            Docket docket = createDocket(openApiProperties);
            configurableBeanFactory.registerSingleton(openApiProperties.getTitle(), docket);
            docketList.add(docket);
            return docketList;
        }

        // 分组创建
        for (String groupName : openApiProperties.getDocket().keySet()) {
            OpenApiProperties.DocketInfo docketInfo = openApiProperties.getDocket().get(groupName);

            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(docketInfo.getTitle().isEmpty() ? openApiProperties.getTitle() : docketInfo.getTitle())
                    .description(docketInfo.getDescription().isEmpty() ? openApiProperties.getDescription() : docketInfo.getDescription())
                    .version(docketInfo.getVersion().isEmpty() ? openApiProperties.getVersion() : docketInfo.getVersion())
                    .license(docketInfo.getLicense().isEmpty() ? openApiProperties.getLicense() : docketInfo.getLicense())
                    .licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? openApiProperties.getLicenseUrl() : docketInfo.getLicenseUrl())
                    .contact(new Contact(
                            docketInfo.getContact().getName().isEmpty() ? openApiProperties.getContact().getName() : docketInfo.getContact().getName(),
                            docketInfo.getContact().getUrl().isEmpty() ? openApiProperties.getContact().getUrl() : docketInfo.getContact().getUrl(),
                            docketInfo.getContact().getEmail().isEmpty() ? openApiProperties.getContact().getEmail() : docketInfo.getContact().getEmail()
                    ))
                    .termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty() ? openApiProperties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl())
                    .build();

            // base-path处理
            // 当没有配置任何path的时候，解析/**
            if (docketInfo.getIncludePath().isEmpty()) {
                docketInfo.getIncludePath().add("/**");
            }
            List<Predicate<String>> includePath = new ArrayList<>(docketInfo.getIncludePath().size());
            for (String path : docketInfo.getIncludePath()) {
                includePath.add(ant(path));
            }

            // exclude-path处理
            List<Predicate<String>> excludePath = new ArrayList<>(docketInfo.getExcludePath().size());
            for (String path : docketInfo.getExcludePath()) {
                excludePath.add(ant(path));
            }
            List<Parameter> parameters = assemblyGlobalOperationParameters(openApiProperties.getGlobalOperationParameters(),
                    docketInfo.getGlobalOperationParameters());

            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .host(openApiProperties.getHost())
                    .apiInfo(apiInfo)
                    .globalOperationParameters(parameters)
                    .groupName(docketInfo.getGroup())
                    .select()
                    .apis(basePackage(docketInfo.getBasePackage()))
//                    .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(includePath)))
                    .build()
                    .securitySchemes(securitySchemes(openApiProperties.getAuthorization(), docketInfo.getAuthorization(), openApiProperties.getApiKeys(), docketInfo.getApiKeys()))
                    .securityContexts(securityContexts(openApiProperties.getAuthorization(), docketInfo.getAuthorization()))
//                    .globalResponseMessage(RequestMethod.GET, getResponseMessages())
//                    .globalResponseMessage(RequestMethod.POST, getResponseMessages())
//                    .globalResponseMessage(RequestMethod.PUT, getResponseMessages())
//                    .globalResponseMessage(RequestMethod.DELETE, getResponseMessages())
                    .extensions(openApiExtensionResolver.buildExtensions(docketInfo.getGroup()));

            configurableBeanFactory.registerSingleton(groupName, docket);
            docketList.add(docket);
        }
        return docketList;
    }

    /**
     * 创建 Docket对象
     *
     * @param OpenApiProperties swagger配置
     * @return Docket
     */
    private Docket createDocket(OpenApiProperties OpenApiProperties) {
        //API 基础信息
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(OpenApiProperties.getTitle())
                .description(OpenApiProperties.getDescription())
                .version(OpenApiProperties.getVersion())
                .license(OpenApiProperties.getLicense())
                .licenseUrl(OpenApiProperties.getLicenseUrl())
                .contact(new Contact(OpenApiProperties.getContact().getName(),
                        OpenApiProperties.getContact().getUrl(),
                        OpenApiProperties.getContact().getEmail()))
                .termsOfServiceUrl(OpenApiProperties.getTermsOfServiceUrl())
                .build();

        // base-path处理
        // 当没有配置任何path的时候，解析/**
        if (OpenApiProperties.getIncludePath().isEmpty()) {
            OpenApiProperties.getIncludePath().add("/**");
        }
        List<Predicate<String>> basePath = new ArrayList<>();
        for (String path : OpenApiProperties.getIncludePath()) {
            basePath.add(ant(path));
        }

        // exclude-path处理
        List<Predicate<String>> excludePath = new ArrayList<>();
        for (String path : OpenApiProperties.getExcludePath()) {
            excludePath.add(ant(path));
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .host(OpenApiProperties.getHost())
                .apiInfo(apiInfo)
                .groupName(OpenApiProperties.getGroup())
                .globalOperationParameters(
                        buildGlobalOperationParametersFromOpenApiProperties(
                                OpenApiProperties.getGlobalOperationParameters()))
                .select()

                .apis(basePackage(OpenApiProperties.getBasePackage()))
//                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                .build()
                .securitySchemes(securitySchemes(OpenApiProperties.getAuthorization(), null, OpenApiProperties.getApiKeys(), null))
                .securityContexts(securityContexts(OpenApiProperties.getAuthorization(), null))
//                .globalResponseMessage(RequestMethod.GET, getResponseMessages())
//                .globalResponseMessage(RequestMethod.POST, getResponseMessages())
//                .globalResponseMessage(RequestMethod.PUT, getResponseMessages())
//                .globalResponseMessage(RequestMethod.DELETE, getResponseMessages())
                .extensions(openApiExtensionResolver.buildExtensions(OpenApiProperties.getGroup()));
//                .pathProvider(new ExtRelativePathProvider(servletContext, OpenApiProperties.getBasePath()));
    }

    private List<ResponseMessage> getResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder().code(0).message("成功").build(),
                new ResponseMessageBuilder().code(-1).message("系统繁忙").build(),
                new ResponseMessageBuilder().code(-2).message("服务超时").build(),
                new ResponseMessageBuilder().code(40001).message("会话超时，请重新登录").build(),
                new ResponseMessageBuilder().code(40003).message("缺少token参数").build()
        );
    }

    /**
     * 默认的全局鉴权策略
     */
    private List<SecurityReference> defaultAuth(OpenApiProperties.Authorization authorization) {
        ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorization.getAuthorizationScopeList()
                .forEach(authorizationScope -> authorizationScopeList.add(new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[authorizationScopeList.size()];
        return Collections.singletonList(SecurityReference.builder()
                .reference(authorization.getName())
                .scopes(authorizationScopeList.toArray(authorizationScopes))
                .build());
    }

    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     * 感觉这里设置了没什么卵用？
     */
    private List<SecurityContext> securityContexts(OpenApiProperties.Authorization globalAuthorization,
                                                   OpenApiProperties.Authorization docketAuthorization) {
        OpenApiProperties.Authorization authorization = docketAuthorization == null ? globalAuthorization : docketAuthorization;
        return authorization == null ? Collections.emptyList()
                : Collections.singletonList(SecurityContext.builder()
                .securityReferences(defaultAuth(authorization))
                .forPaths(PathSelectors.regex(authorization.getAuthRegex()))
                .build());
    }

    /**
     * 控制 Authorize 界面
     */

    private List<SecurityScheme> securitySchemes(OpenApiProperties.Authorization globalAuthorization,
                                                 OpenApiProperties.Authorization docketAuthorization,
                                                 List<OpenApiProperties.ApiKey> globalApiKeys,
                                                 List<OpenApiProperties.ApiKey> docketApiKeys) {
        List<SecurityScheme> list = new ArrayList<>();

        OpenApiProperties.Authorization authorization = docketAuthorization == null ? globalAuthorization : docketAuthorization;

        if (authorization != null) {
            ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
            authorization.getAuthorizationScopeList().forEach(authorizationScope ->
                    authorizationScopeList.add(new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
            ArrayList<GrantType> grantTypes = new ArrayList<>();
            authorization.getTokenUrlList().forEach(tokenUrl -> grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(tokenUrl)));
            OAuth oAuth = new OAuth(authorization.getName(), authorizationScopeList, grantTypes);
            list.add(oAuth);
        }

        List<OpenApiProperties.ApiKey> apiKeys = CollUtil.isEmpty(docketApiKeys) ? globalApiKeys : docketApiKeys;
        if (CollUtil.isNotEmpty(apiKeys)) {
            List<OpenApiProperties.ApiKey> allApiKeys = new ArrayList<>(apiKeys);
            if (globalApiKeys != null && !apiKeys.equals(globalApiKeys)) {
                allApiKeys = new ArrayList<>(globalApiKeys);

                Set<String> docketNames = allApiKeys.stream()
                        .map(OpenApiProperties.ApiKey::getKeyname)
                        .collect(Collectors.toSet());

                for (OpenApiProperties.ApiKey ak : docketApiKeys) {
                    if (!docketNames.contains(ak.getKeyname())) {
                        allApiKeys.add(ak);
                    }
                }
            }
            List<ApiKey> apiKeyList = allApiKeys.stream().map(item -> new ApiKey(item.getName(), item.getKeyname(), item.getPassAs())).collect(Collectors.toList());
            list.addAll(apiKeyList);
        }
        return list;
    }

    private List<Parameter> buildGlobalOperationParametersFromOpenApiProperties(
            List<OpenApiProperties.GlobalOperationParameter> globalOperationParameters) {
        List<Parameter> parameters = Lists.newArrayList();

        if (Objects.isNull(globalOperationParameters)) {
            return parameters;
        }
        for (OpenApiProperties.GlobalOperationParameter globalOperationParameter : globalOperationParameters) {
            parameters.add(new ParameterBuilder()
                    .name(globalOperationParameter.getName())
                    .description(globalOperationParameter.getDescription())
                    .defaultValue(globalOperationParameter.getDefaultValue())
                    .required(globalOperationParameter.getRequired())
                    .allowMultiple(globalOperationParameter.getAllowMultiple())
                    .parameterType(globalOperationParameter.getParameterType())
                    .modelRef(new ModelRef(globalOperationParameter.getModelRef()))
                    .hidden(globalOperationParameter.getHidden())
                    .pattern(globalOperationParameter.getPattern())
                    .collectionFormat(globalOperationParameter.getCollectionFormat())
                    .allowEmptyValue(globalOperationParameter.getAllowEmptyValue())
                    .order(globalOperationParameter.getOrder())
                    .build());
        }
        return parameters;
    }

    /**
     * 局部参数按照name覆盖局部参数
     */
    private List<Parameter> assemblyGlobalOperationParameters(
            List<OpenApiProperties.GlobalOperationParameter> globalOperationParameters,
            List<OpenApiProperties.GlobalOperationParameter> docketOperationParameters) {

        if (Objects.isNull(docketOperationParameters) || docketOperationParameters.isEmpty()) {
            return buildGlobalOperationParametersFromOpenApiProperties(globalOperationParameters);
        }

        Set<String> docketNames = docketOperationParameters.stream()
                .map(OpenApiProperties.GlobalOperationParameter::getName)
                .collect(Collectors.toSet());

        List<OpenApiProperties.GlobalOperationParameter> resultOperationParameters = Lists.newArrayList();

        if (Objects.nonNull(globalOperationParameters)) {
            for (OpenApiProperties.GlobalOperationParameter parameter : globalOperationParameters) {
                if (!docketNames.contains(parameter.getName())) {
                    resultOperationParameters.add(parameter);
                }
            }
        }

        resultOperationParameters.addAll(docketOperationParameters);
        return buildGlobalOperationParametersFromOpenApiProperties(resultOperationParameters);
    }
}
