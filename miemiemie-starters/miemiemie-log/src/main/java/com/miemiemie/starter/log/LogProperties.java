package com.miemiemie.starter.log;

import com.miemiemie.starter.log.handler.Slf4jLogHandler;
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
        private Class<? extends LogHandler> defaultHandler = Slf4jLogHandler.class;

    }

    /**
     * controller日志
     */
    @Data
    public static class Controller {

        /**
         * 是否启用controller日志记录
         */
        private boolean enable = true;

    }

}
