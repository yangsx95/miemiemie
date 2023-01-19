package com.miemiemie.starter.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangshunxiang
 * @since 2023/1/18
 */
@Api(tags = "Hello")
@RestController
public class HelloController {

    @Resource
    private ApplicationContext context;

    @ApiOperation("说hello")
    @GetMapping("/sayHello")
    public String sayHello() {
        System.out.println();
        return "hello";
    }

}
