package com.miemiemie.starter.xss.ignore;

import jakarta.servlet.http.HttpServletRequest;

/**
 * XSS请求忽略器
 *
 * @author yangshunxiang
 * @since 2023/4/2
 */
public interface XssRequestIgnorer {

    /**
     * 判断是否忽略当前请求，被忽略的请求不会被XSS过滤器并进行XSS处理
     *
     * @return 是否忽略
     */
    default boolean isIgnoredRequest(HttpServletRequest request) {
        return false;
    }

    /**
     * 判断是否忽略请求的目标参数，被忽略的请求参数值不会进行XSS处理
     *
     * @param paramName  请求参数名
     * @param paramValue 请求参数值
     * @return 是否忽略
     */
    default boolean isIgnoreParam(String paramName, String paramValue) {
        return false;
    }

    /**
     * 判断是否忽略请求的目标头，被忽略的请求头不会进行XSS处理
     *
     * @param headerName  请求头名
     * @param headerValue 请求头值
     * @return 是否忽略
     */
    default boolean isIgnoreHeader(String headerName, String headerValue) {
        return false;
    }
}
