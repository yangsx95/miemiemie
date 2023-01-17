package com.miemiemie.starter.mybatisplus.entity;

import com.miemiemie.starter.mybatisplus.enums.Belief;
import com.miemiemie.starter.mybatisplus.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    private String pname;

    private Gender gender;

    private Belief belief;

}
