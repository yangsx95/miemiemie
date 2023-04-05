package com.miemiemie.starter.log.annotations;

import com.miemiemie.starter.log.LogHandler;
import com.miemiemie.starter.log.handler.DefaultLogHandler;
import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.*;

/**
 * 日志注解
 * 如果标记在方法上，记录方法的日志
 * 如果标记在类上，记录类中所有方法的日志
 *
 * @author yangshunxiang
 * @since 2023/4/4
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {

    /**
     * 是否启用日志记录
     */
    boolean enable() default true;

    /**
     * 日志记录的级别
     */
    LogLevel level() default LogLevel.INFO;

    /**
     * 日志的描述信息，一般是业务描述
     */
    String value() default "";

    /**
     * 是否记录方法的参数
     */
    boolean recordParam() default true;

    /**
     * 是否记录方法的返回值
     */
    boolean recordReturn() default true;

    /**
     * 当方法发生异常时，是否记录异常信息；注意，如果方法发生异常，方法的参数一定会被记录
     */
    boolean recordException() default true;

    /**
     * 是否记录方法的执行时间
     */
    boolean recordTime() default false;

    /**
     * 日志处理器
     */
    Class<? extends LogHandler>[] handlers() default DefaultLogHandler.class;
}