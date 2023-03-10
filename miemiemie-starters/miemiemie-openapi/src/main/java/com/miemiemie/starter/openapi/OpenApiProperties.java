package com.miemiemie.starter.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static springfox.documentation.spring.web.plugins.Docket.DEFAULT_GROUP_NAME;

/**
 * @author yangshunxiang
 * @since 2023/1/19
 */
@Data
@ConfigurationProperties(prefix = "miemiemie.openapi")
public class OpenApiProperties {

    private List<DocketInfo> docketInfoList = new ArrayList<>();

    @Data
    public static class DocketInfo {

        /**
         * 是否启用
         */
        private boolean enabled = true;

        /**
         * 组名
         */
        private String groupName = DEFAULT_GROUP_NAME;

        /**
         * host信息
         **/
        private String host = "";

        /**
         * Api基本信息
         */
        private ApiInfo apiInfo = new ApiInfo();

        /**
         * API选择器
         */
        private Selector selector = new Selector();

        private List<GlobalOperationParameter> globalOperationParameters;
    }

    @Data
    public static class ApiInfo {
        /**
         * 文档版本号
         */
        private final String version = "1.0";
        /**
         * 文件标题
         */
        private final String title = "API文档";
        /**
         * 文档描述
         */
        private final String description = "MIEMIEMIE API文档";
        /**
         * 服务条款URL
         **/
        private final String termsOfServiceUrl = "";
        /**
         * 许可证
         */
        private final String license = "";
        /**
         * 许可证url
         */
        private final String licenseUrl = "";

        /**
         * 联系人信息
         */
        private final Contact contact = new Contact();

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";
    }

    /**
     * Api选择器
     */
    @Data
    public static class Selector {

        private Apis apis = new Apis();

        private Paths paths = new Paths();

        @Data
        public static class Apis {
            private String basePackage;
            private Class<? extends Annotation> withMethodAnnotation;
        }

        @Data
        public static class Paths {
            private String regex;
            private String ant;
        }
    }

    @Data
    public static class SecurityScheme {
        private String name;
        private String type;
        private String description;
    }


    /**
     * 排序
     */
    private Integer order = 1;

    /**
     * 全局参数配置
     **/
    private List<GlobalOperationParameter> globalOperationParameters;

    @Setter
    @Getter
    public static class GlobalOperationParameter {


        /**
         * 参数名
         **/
        private String name;

        /**
         * 描述信息
         **/
        private String description = "全局参数";

        /**
         * 指定参数类型
         **/
        private String modelRef = "String";
        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType = "header";
        /**
         * 参数是否必须传
         **/
        private Boolean required = false;
        private Boolean allowMultiple = false;
        private AllowableValues allowableValues;
        private Boolean hidden = false;
        private String pattern = "";
        private String collectionFormat = "";
        /**
         * 默认值
         */
        private String defaultValue = "";
        /**
         * 允许为空
         */
        private Boolean allowEmptyValue = true;
        /**
         * 排序
         */
        private int order = 1;

    }

    @Data
    @NoArgsConstructor
    public static class Authorization {


        /**
         * 鉴权策略ID，需要和SecurityReferences ID保持一致
         */
        private String name = "";

        /**
         * 需要开启鉴权URL的正则
         */
        private String authRegex = "^.*$";

        /**
         * 鉴权作用域列表
         */
        private List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        private List<String> tokenUrlList = new ArrayList<>();

    }

    @Data
    @NoArgsConstructor
    public static class AuthorizationScope {


        /**
         * 作用域名称
         */
        private String scope = "";

        /**
         * 作用域描述
         */
        private String description = "";

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiKey {
        private String name;
        private String keyname;
        private String passAs = "header";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AllowableValues {
        private List<String> values;
        private String valueType;
    }

}
