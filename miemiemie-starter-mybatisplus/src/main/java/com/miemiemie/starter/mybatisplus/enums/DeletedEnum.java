package com.miemiemie.starter.mybatisplus.enums;

import com.miemiemie.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeletedEnum implements CommonEnum<Integer> {
    /**
     * 未删除
     */
    NOT_DELETED(0, "未删除"),

    /**
     * 已删除
     */
    DELETED(1, "已删除"),
    ;

    private final Integer code;

    private final String message;

}
