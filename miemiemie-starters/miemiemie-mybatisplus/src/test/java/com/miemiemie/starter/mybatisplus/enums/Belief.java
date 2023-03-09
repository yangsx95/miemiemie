package com.miemiemie.starter.mybatisplus.enums;

import com.miemiemie.starter.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Belief implements CommonEnum<Integer, String> {

    NONE(0, "无"),
    CHRISTIANITY(1, "基督"),
    BUDDHISM(2, "佛教"),
    ISLAMICSM(3, "伊斯兰教"),
    TAOISM(4, "道教"),
    OTHER(5, "其他"),
    ;

    private final Integer code;

    private final String message;
}
