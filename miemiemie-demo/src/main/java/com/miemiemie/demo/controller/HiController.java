package com.miemiemie.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yangshunxiang
 * @since  2022/12/8
 */
@Controller
@RequestMapping("/hi")
public class HiController {

    @GetMapping("/say")
    @ResponseBody
    public String sayHello(@RequestParam(name = "name", required = false) String name) {
        return "hello " + name;
    }


}
