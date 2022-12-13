package com.miemiemie.demo.controller;

import com.miemiemie.demo.pojo.entity.Person;
import com.miemiemie.demo.pojo.req.PersonReq;
import com.miemiemie.starter.web.annotation.PathRestController;
import org.springframework.web.bind.annotation.*;

@PathRestController("/person")
public class PersonController {
    @PutMapping
    public Long add(@RequestBody PersonReq personReq) {
        return null;
    }

    @PostMapping
    public void update(@RequestBody PersonReq personReq) {

    }

    @GetMapping("/{id}")
    public Person detail(@PathVariable(name = "id") Long id) {

        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
    }

    @GetMapping("/page")
    public void pageList() {

    }
}
