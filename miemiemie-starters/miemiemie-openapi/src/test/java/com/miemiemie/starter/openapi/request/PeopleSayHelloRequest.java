package com.miemiemie.starter.openapi.request;

import com.miemiemie.starter.openapi.enums.TypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "人")
public class PeopleSayHelloRequest {

    @Schema(name = "姓名")
    private String name;

    @Schema(name = "类型")
    private TypeEnum typeEnum;
}
