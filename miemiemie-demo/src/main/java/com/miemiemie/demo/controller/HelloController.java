package com.miemiemie.demo.controller;

import com.miemiemie.starter.web.annotation.PathRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yangshunxiang
 * @since 2022/12/8
 */
@PathRestController("/hello")
public class HelloController {

    @GetMapping("/say")
    public String sayHello(@RequestParam(name = "name", required = false) String name) {
        return "hello " + name;
    }

}
