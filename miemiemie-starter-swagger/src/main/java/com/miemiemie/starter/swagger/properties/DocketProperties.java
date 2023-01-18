package com.miemiemie.starter.swagger.properties;

import lombok.Data;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;

import java.util.List;

/**
 * Swagger Docket配置对象
 *
 * @author yangshunxiang
 * @since 2023/1/18
 */
@Data
public class DocketProperties {

    /**
     * 文档类型
     */
    private DocumentationType docType = DocumentationType.OAS_30;

    /**
     * apiInfo
     */
    private ApiInfoProperties apiInfo;

    /**
     * 分组名称
     */
    private String groupName;


    private    List<RequestParameter> globalRequestParameters;
}
