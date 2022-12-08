package com.miemiemie.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangshunxiang
 * @since 2022/12/8
 */
@RestController
@RequestMapping("/ha")
public class HaController {

    @GetMapping("/say")
    public String sayHa(@RequestParam(name = "name", required = false) String name) {
        return "ha " + name;
    }


}
