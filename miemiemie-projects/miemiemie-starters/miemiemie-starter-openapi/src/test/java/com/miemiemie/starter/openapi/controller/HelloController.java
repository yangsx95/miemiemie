package com.miemiemie.starter.openapi.controller;

import com.miemiemie.starter.openapi.enums.TypeEnum;
import com.miemiemie.starter.openapi.request.PeopleSayHelloRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangshunxiang
 * @since 2023/1/18
 */
@Api(tags = "Hello")
@RestController
public class HelloController {

    @ApiOperation("说hello")
    @GetMapping("/sayHello")
    public String sayHello(TypeEnum typeEnum) {
        return "hello " + typeEnum;
    }

    @ApiOperation("说hello2")
    @GetMapping("/sayHello2")
    public String peopleSayHello (@RequestBody PeopleSayHelloRequest request) {
        return request.getTypeEnum().getMessage() + "  " + request.getName();
    }
}
