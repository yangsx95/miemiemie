package com.miemiemie.demo.pojo.entity;

import com.miemiemie.demo.enums.Belief;
import com.miemiemie.demo.enums.Gender;
import com.miemiemie.starter.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    private String name;

    private Gender gender;

    private Belief belief;

}
