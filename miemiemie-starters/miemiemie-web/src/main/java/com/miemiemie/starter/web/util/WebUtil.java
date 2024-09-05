package com.miemiemie.starter.web.util;

import com.miemiemie.starter.web.controller.ErrorDispatcherController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.miemiemie.starter.web.controller.ErrorDispatcherController.EXCEPTION_FORWARD_PARAM_NAME;
import static com.miemiemie.starter.web.controller.ErrorDispatcherController.EXCEPTION_FORWARD_PATH;

/**
 * @author 杨顺翔
 * @since 2024/9/6
 */
public final class WebUtil {

    private WebUtil() {
    }

    /**
     * 过滤器中的异常信息，无法被全局异常处理器处理
     * 可以通过此方法将异常转发道通过ErrorDispatcherController，以此以此来经过全局异常处理器
     *
     * @param request   请求对象
     * @param response  响应对象
     * @param exception 异常西悉尼
     * @throws ServletException 参见forward
     * @throws IOException      参见forward
     * @see ErrorDispatcherController
     */
    public static void forwardToError(HttpServletRequest request, HttpServletResponse response, Exception exception)
            throws ServletException, IOException {
        request.setAttribute(EXCEPTION_FORWARD_PARAM_NAME, exception);
        request.getRequestDispatcher(EXCEPTION_FORWARD_PATH).forward(request, response);
    }
}
