package com.miemiemie.starter.web.aspect;

import com.github.f4b6a3.ulid.Ulid;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * 所有的controller方法
     */
    @Pointcut(" @within(org.springframework.stereotype.Controller) " +
            "|| @within(org.springframework.web.bind.annotation.RestController) " +
            "|| @within(com.miemiemie.starter.web.annotation.PathRestController)"
    )
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
                .collect(Collectors.joining(" "))
                .trim();
        HttpServletRequest request = attributes.getRequest();
        log.info("http request\t[{}] {} => {}", request.getMethod(), request.getRequestURI(), params);
    }

    @AfterReturning(value = "restLog()", returning = "returnObject")
    public void afterReturning(Object returnObject) {
        long time = 0;
        try {
            time = System.currentTimeMillis() - Ulid.getTime(MDC.get(TRACE_ID));
        } catch (Exception e) {
            log.error("日志打印出错，解析TRACE_ID失败", e);
        }

        log.info("http response\t{}ms => {}", time, returnObject);
        MDC.remove(TRACE_ID);
    }

}

