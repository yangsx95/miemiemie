package com.miemiemie.starter.web.exception.handler;

import com.miemiemie.core.enums.ResultStatusEnum;
import com.miemiemie.core.exception.BizException;
import com.miemiemie.core.result.Result;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
     * 处理参数校验异常
     *
     * @param req 请求对象
     * @param e   异常对象
     * @return 返回体
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Void> checkExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error("接口 {} 字段验证不通过：{}", req.getRequestURI(), e.getMessage());
        return Result.build(ResultStatusEnum.PARAMETER_CHECK_FAIL.getCode(), generateFieldValidErrMsg(e.getBindingResult()));
    }

    /**
     * 处理参数绑定异常
     *
     * @param req 请求对象
     * @param e   异常对象
     * @return 返回体
     */
    @ExceptionHandler(value = BindException.class)
    public Result<Void> bindExceptionHandler(HttpServletRequest req, BindException e) {
        log.error("接口 {} 字段验证不通过：{}", req.getRequestURI(), e.getMessage());
        return Result.build(ResultStatusEnum.PARAMETER_CHECK_FAIL.getCode(), generateFieldValidErrMsg(e.getBindingResult()));
    }

    private String generateFieldValidErrMsg(@NonNull BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors()
                .forEach(err -> {
                            if (StringUtils.hasText(err.getDefaultMessage())) {
                                errorMessage.append(err.getDefaultMessage());
                            }
                        }
                );
        return errorMessage.toString();
    }

    /**
     * 处理自定义的业务异常
     *
     * @param req 请求对象
     * @param e   异常对象
     * @return 返回体
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = BizException.class)
    public Result<Void> bizExceptionHandler(HttpServletRequest req, BizException e) {
        log.error("接口 {} 发生业务异常：{}", req.getRequestURI(), e.getErrorMsg());
        return Result.build(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理RuntimeException
     *
     * @param req 请求对象
     * @param e   异常对象
     * @return 返回体
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> runtimeExceptionHandler(HttpServletRequest req, RuntimeException e) {
        log.error("接口 {} 发生未知异常:", req.getRequestURI(), e);
        return Result.fail(ResultStatusEnum.SERVER_ERROR);
    }

    /**
     * 处理其他异常
     *
     * @param req 请求对象
     * @param e   异常对象
     * @return 返回体
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Result<Void> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("接口 {} 发生未知异常:", req.getRequestURI(), e);
        return Result.fail(ResultStatusEnum.SERVER_ERROR);
    }


}
