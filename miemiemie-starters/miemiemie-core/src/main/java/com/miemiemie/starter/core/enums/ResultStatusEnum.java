package com.miemiemie.starter.core.enums;

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
    SUCCESS(200, "请求成功"),

    /**
     * 业务通用异常
     */
    BIZ_ERROR(300, "处理失败"),

    /**
     * 重复的请求
     */
    DUPLICATE_REQ(401, "不可重复的请求"),

    /**
     * 参数校验失败
     */
    PARAMETER_CHECK_FAIL(402, "参数校验失败"),

    /**
     * 认证失败
     */
    AUTH_FAIL(403, "认证失败"),

    /**
     * 系统未知错误
     */
    SERVER_ERROR(500, "服务器内部错误"),

    /**
     * 服务器繁忙
     */
    SERVER_BUSY(511, "服务器繁忙，请稍后再试"),

    /**
     * 上传文件失败
     */
    FILE_UPLOAD_ERROR(515, "文件上传失败"),

    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_ERROR(516, "文件下载失败"),

    /**
     * 调用内部服务失败
     */
    INVOKE_INTERNAL_SERVICE_FAIL(601, "服务调用失败"),

    ;

    private final Integer code;


    private final String message;
}