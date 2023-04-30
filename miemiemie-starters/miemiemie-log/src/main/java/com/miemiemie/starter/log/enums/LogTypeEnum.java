package com.miemiemie.starter.log.enums;

import com.miemiemie.starter.core.enums.NameCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yangshunxiang
 * @since 2023/4/30
 */
@Getter
@AllArgsConstructor
public enum LogTypeEnum implements NameCodeEnum {

    CONTROLLER("接口日志"),

    METHOD("方法日志"),
    ;

    private final String message;

}
