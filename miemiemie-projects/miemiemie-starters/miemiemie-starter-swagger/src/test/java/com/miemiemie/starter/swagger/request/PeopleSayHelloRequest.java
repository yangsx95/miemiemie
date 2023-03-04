package com.miemiemie.starter.swagger.request;

import com.miemiemie.starter.swagger.enums.TypeEnum;
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
