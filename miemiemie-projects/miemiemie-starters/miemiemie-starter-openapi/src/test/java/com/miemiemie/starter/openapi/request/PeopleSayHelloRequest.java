package com.miemiemie.starter.openapi.request;

import com.miemiemie.starter.openapi.enums.TypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PeopleSayHelloRequest {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("类型")
    private TypeEnum typeEnum;
}
