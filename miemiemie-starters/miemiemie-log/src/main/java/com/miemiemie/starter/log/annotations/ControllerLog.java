package com.miemiemie.starter.log.annotations;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.*;

/**
 * 控制器日志注解
 *
 * @author yangshunxiang
 * @since 2023/4/30
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLog {

    /**
     * 日志记录的级别
     */
    LogLevel level() default LogLevel.INFO;

    /**
     * 日志的描述信息，一般是业务描述
     */
    String message() default "";

}
