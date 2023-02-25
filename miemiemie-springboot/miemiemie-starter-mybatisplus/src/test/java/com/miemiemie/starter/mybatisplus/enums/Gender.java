package com.miemiemie.starter.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MAN(0, "男"),
    WOMAN(1, "女"),
    ;

    @EnumValue
    private final Integer code;

    private final String message;

}
