package com.miemiemie.starter.log;

import com.miemiemie.starter.log.annotations.MethodLog;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author yangshunxiang
 * @since 2023/4/4
 */

@Getter
public record LogRecord(Object invoker,
                        Class<?> invokerClass,
                        MethodLog methodLog,
                        Method method,
                        Object[] args,
                        Object result,
                        Throwable throwable,
                        long startTime,
                        long endTime) {
}