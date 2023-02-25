package com.miemiemie.starter.validation.vo;

import com.miemiemie.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yangshunxiang
 * @since 2023/1/13
 */
@AllArgsConstructor
@Getter
public enum Gender implements CommonEnum<Integer, String> {
    MAN(0, "男"),
    WOMAN(1, "女"),
    ;

    private final Integer code;

    private final String message;
}
