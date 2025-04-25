package com.miemiemie.starter.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用0 1 是否枚举
 *
 * @author 杨顺翔
 * @since 2025/04/25
 */
@Getter
@AllArgsConstructor
public enum YesOrNoEnum implements CommonEnum<Integer, String> {
    YES(1, "是"),
    NO(0, "否"),
    ;

    private final Integer code;

    private final String message;

}
