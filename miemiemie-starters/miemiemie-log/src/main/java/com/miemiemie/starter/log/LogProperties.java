package com.miemiemie.starter.log;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangshunxiang
 * @since 2023/4/5
 */
@Data
@ConfigurationProperties(prefix = LogProperties.MIEMIEMIE_LOG)
public class LogProperties {

    public static final String MIEMIEMIE_LOG = "miemiemie.log";

    private Method method;

    private Controller controller;

    /**
     * 方法日志
     */
    @Data
    public static class Method {

        /**
         * 是否启用方法日志记录
         */
        private boolean enable = true;

        /**
         * 默认日志处理器
         */
        private Class<? extends LogHandler> defaultHandler;

    }

    /**
     * controller日志
     */
    @Data
    public static class Controller {

        /**
         * 是否启用自动的controller日志记录
         */
        private boolean enable = true;

        /**
         * 是否自动开启Controller日志打印
         */
        private boolean auto = true;
    }

}
