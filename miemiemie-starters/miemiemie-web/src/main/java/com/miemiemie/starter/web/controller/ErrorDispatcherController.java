package com.miemiemie.starter.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 过滤器中的异常信息，无法被全局异常处理器处理
 * 可以将异常转发道通过此控制器，以此以此来经过全局异常处理器
 *
 * @author 杨顺翔
 * @since 2024/9/6
 */
@RestController
public class ErrorDispatcherController {

    public static final String EXCEPTION_FORWARD_PATH = "/error";
    public static final String EXCEPTION_FORWARD_PARAM_NAME = "exception";

    @PostMapping(EXCEPTION_FORWARD_PATH)
    public void throwException(HttpServletRequest request) throws Exception {
        throw (Exception) request.getAttribute(EXCEPTION_FORWARD_PARAM_NAME);
    }
}
