package com.miemiemie.starter.web.exception;

import com.miemiemie.starter.web.result.ResultStatusEnum;
import com.miemiemie.starter.web.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author 杨顺翔
 * @since 2022/09/11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @param req 请求对象
     * @param e   异常对象
     * @return 返回体
     */
    @ExceptionHandler(value = BizException.class)
    public Result<Void> bizExceptionHandler(HttpServletRequest req, BizException e) {
        log.error("系统发生业务异常：{}", e.getErrorMsg());
        return Result.build(e.getErrorCode(), e.getErrorMsg());
    }


    /**
     * 处理其他异常
     *
     * @param req 请求对象
     * @param e   异常对象
     * @return 返回体
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Void> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("系统发生未知异常:", e);
        return Result.fail(ResultStatusEnum.UNKNOWN_ERROR);
    }

}
