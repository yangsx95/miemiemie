package com.miemiemie.starter.log.entity;

import com.miemiemie.starter.log.enums.LogTypeEnum;
import lombok.Data;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;

/**
 * @author yangshunxiang
 * @since 2023/4/30
 */
@Data
public class LogRecord {

    /**
     * 日志类型
     */
    private LogTypeEnum logTypeEnum = LogTypeEnum.METHOD;

    /**
     * 日志信息
     */
    private String message;

    /**
     * 日志级别
     */
    private LogLevel level;

    /**
     * 当前所在的方法信息
     */
    private MethodInfo methodInfo = new MethodInfo();

    /**
     * 接口controller日志信息，只有在日志类型为CONTROLLER时才有值
     */
    private ControllerInfo controllerInfo = new ControllerInfo();

    @Data
    public static class MethodInfo {

        /**
         * 目标方法
         */
        private Method method;

        /**
         * 目标方法的参数
         */
        private Object[] args;

        /**
         * 目标方法是否执行成功
         */
        private boolean success;

        /**
         * 目标方法的返回值
         */
        private Object result;

        /**
         * 方法异常信息
         */
        private Throwable throwable;

    }

    @Data
    public static class ControllerInfo {

        /**
         * 请求的接口路径
         */
        private String url;

        /**
         * 请求的IP地址
         */
        private String requestIp;

        /**
         * http请求方法
         */
        private HttpMethod httpMethod;

        /**
         * 目标接口是否200
         */
        private boolean success;

        /**
         * 请求id
         */
        private String requestId;
    }

    /**
     * 执行开始时间
     */
    private long startTime;

    /**
     * 执行结束时间
     */
    private long endTime;

}
