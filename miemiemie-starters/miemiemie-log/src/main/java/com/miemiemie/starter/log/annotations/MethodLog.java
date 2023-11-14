package com.miemiemie.starter.log.annotations;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.*;

/**
 * 方法日志注解，如果标记在方法上，记录方法的日志；如果标记在类上，记录类中所有方法的日志
 *
 * @author yangshunxiang
 * @since 2023/4/4
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {

    /**
     * 日志记录的级别
     */
    LogLevel level() default LogLevel.INFO;

    /**
     * 日志的描述信息，一般是业务描述
     */
    String message() default "";

}