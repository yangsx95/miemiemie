package com.miemiemie.starter.web.exception.handler;

import com.miemiemie.starter.core.enums.ResultStatusEnum;
import com.miemiemie.starter.core.result.Result;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * spring-validation 相关异常处理
 *
 * @author yangshunxiang
 * @since 2023/1/13
 */
@Slf4j
@ConditionalOnClass(ConstraintViolationException.class)
@RestControllerAdvice
public class ValidationExceptionHandler {


    @ExceptionHandler({ConstraintViolationException.class})
    public Result<Void> validateException(ConstraintViolationException e, HttpServletRequest req) {
        log.error("接口 {} 字段验证不通过：{}", req.getRequestURI(), e.getMessage());
        return Result.build(ResultStatusEnum.PARAMETER_CHECK_FAIL.getCode(), generateFieldValidErrMsg(e.getConstraintViolations()));
    }

    private String generateFieldValidErrMsg(@NonNull Set<ConstraintViolation<?>> set) {
        StringBuilder errorMessage = new StringBuilder();
        set.forEach(violation -> errorMessage.append(violation.getMessage()));
        return errorMessage.toString();
    }
}
