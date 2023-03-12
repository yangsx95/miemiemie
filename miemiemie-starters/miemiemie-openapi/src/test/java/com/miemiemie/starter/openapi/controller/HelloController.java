package com.miemiemie.starter.openapi.controller;

import com.miemiemie.starter.openapi.enums.TypeEnum;
import com.miemiemie.starter.openapi.request.PeopleSayHelloRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangshunxiang
 * @since 2023/1/18
 */
@Tag(name = "Hello")
@RestController
public class HelloController {

    @Operation(summary = "说hello", description = "我是描述")
    @GetMapping("/sayHello")
    public String sayHello(@Parameter(name = "类型") TypeEnum typeEnum) {
        return "hello " + typeEnum;
    }

    @Operation(summary = "说hello2", description = "我是描述")
    @GetMapping("/sayHello2")
    public String peopleSayHello (@RequestBody PeopleSayHelloRequest request) {
        return request.getTypeEnum().getMessage() + "  " + request.getName();
    }
}
