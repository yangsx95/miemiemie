package com.miemiemie.starter.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
@Configuration
public class SwaggerConfiguration {

    /**
     * 创建Docket对象
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 设置API的基本信息
                .apiInfo(apiInfo())
                // 创建一个ApiSelectorBuilder 用来指定哪些接口暴露给Swagger展示
                .select()
                // 扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.miemiemie.starter.swagger"))
                // 扫描所有的api注解
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 所有路径的接口，都可以暴露给swagger
                .paths(PathSelectors.any())
                .build()
                // 设置安全模式，swagger可以设置访问token
                .securitySchemes(securitySchemes())
                // 设置需要安全模式的目标上下文
                .securityContexts(securityContexts())
                // 全局请求参数
                .globalRequestParameters(getGlobalRequestParameters())
                // get 和 post请求全局响应参数
                .globalResponses(HttpMethod.GET, getGlobalResponses())
                .globalResponses(HttpMethod.POST, getGlobalResponses())
                ;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    /**
     * 通用的响应信息
     *
     * @return 多条
     */
    private List<Response> getGlobalResponses() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
        return responseList;
    }


    /**
     * 全局请求参数
     *
     * @return 多条
     */
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> list = new ArrayList<>();
        list.add(new RequestParameterBuilder()
                .name("appid")
                .description("平台id")
                .required(true)
                .in(ParameterType.QUERY)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        list.add(new RequestParameterBuilder()
                .name("udid")
                .description("设备的唯一id")
                .required(true)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        list.add(new RequestParameterBuilder()
                // 参数字段名
                .name("version")
                // 参数描述
                .description("客户端的版本号")
                // 是否必须
                .required(true)
                // 参数类型
                .in(ParameterType.HEADER)
                // 数据类型
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        return list;
    }

    /**
     * 配置Api的基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Docket api 文档的标题")
                .description("Docket api 文档的描述")
                .version("api版本")
                .contact(new Contact("api联系人名称", "api联系人url", "api联系人email"))
                .termsOfServiceUrl("https://服务的url.com")
                .license("许可证名称")
                .licenseUrl("许可证url")
                // 额外显示的信息
                .extensions(getVendorExtensions())
                .build();
    }

    private List<VendorExtension> getVendorExtensions() {
        List<VendorExtension> extensions = new ArrayList<>();
        extensions.add(new StringVendorExtension("额外的信息名称", "额外的信息值"));
        return extensions;
    }
}
