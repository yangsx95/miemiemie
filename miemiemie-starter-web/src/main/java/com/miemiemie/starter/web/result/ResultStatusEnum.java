package com.miemiemie.starter.web.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用响应枚举
 *
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Getter
@AllArgsConstructor
public enum ResultStatusEnum implements ResultStatus {

    /**
     * 成功状态
     */
    SUCCESS(1, "请求成功"),

    /**
     * 系统未知错误
     */
    UNKNOWN_ERROR(-1, "未知异常"),

    /**
     * 业务通用异常
     */
    BIZ_ERROR(-10, "处理失败"),

    ;

    private final Integer code;


    private final String message;
}