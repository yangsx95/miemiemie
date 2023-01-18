package com.miemiemie.starter.validation.vo;

import com.miemiemie.starter.validation.annotation.CommonEnumCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yangshunxiang
 * @since  2023/1/13
 */
@Data
public class Person {

    @NotBlank(message = "姓名不可为空")
    private String name;

    @CommonEnumCode(message = "性别不正确", target = Gender.class)
    private Integer gender;

}
