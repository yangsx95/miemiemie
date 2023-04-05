package com.miemiemie.starter.log.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.miemiemie.starter.log.LogHandler;
import com.miemiemie.starter.log.LogProperties;
import com.miemiemie.starter.log.LogRecord;

/**
 * @author yangshunxiang
 * @since 2023/4/4
 */
public class DefaultLogHandler implements LogHandler {

    private final LogProperties logProperties;

    public DefaultLogHandler(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    @Override
    public void handle(LogRecord logRecord) {
        // 根据properties配置的默认handler决定使用谁作为默认handler
        Class<? extends LogHandler> defaultHandler = logProperties.getMethod().getDefaultHandler();
        if (defaultHandler == null) {
            throw new RuntimeException("未配置默认日志处理器");
        }

        LogHandler handler = SpringUtil.getBean(defaultHandler);
        if (handler != null) {
            handler.handle(logRecord);
            return;
        }

        // 如果spring容器中没有找到对应的bean，则使用反射创建一个
        try {
            LogHandler logHandler = defaultHandler.getDeclaredConstructor().newInstance();
            logHandler.handle(logRecord);
        } catch (Exception e) {
            throw new RuntimeException("创建默认日志处理器失败", e);
        }
    }
}
