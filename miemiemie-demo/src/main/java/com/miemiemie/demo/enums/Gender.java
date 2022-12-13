package com.miemiemie.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.miemiemie.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
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
