package com.miemiemie.starter.web.aspect;

import com.github.f4b6a3.ulid.Ulid;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 日志切面
 *
 * @author yangshunxiang
 * @since 2022/12/8
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    public static final String TRACE_ID = "trace-id";

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    public void restLog() {
    }

    @Before("restLog()")
    public void before(JoinPoint joinPoint) {
        MDC.put(TRACE_ID, Ulid.fast().toLowerCase());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return;
        }
        String params = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof ServletRequest || arg instanceof ServletResponse)
                .map(Objects::toString)
                .collect(Collectors.joining(" "));
        log.info("receive request: {} , params: {}", attributes.getRequest().getRequestURI(), params);
    }

    @AfterReturning(value = "restLog()", returning = "returnObject")
    public void afterReturning(Object returnObject) {
        log.info("return response: {}", returnObject);
        MDC.remove(TRACE_ID);
    }

}

