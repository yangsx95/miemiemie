package com.miemiemie.starter.log.event;

import com.miemiemie.starter.log.LogHandler;
import com.miemiemie.starter.log.entity.LogRecord;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 日志事件监听器
 *
 * @author yangshunxiang
 * @since 2023/4/30
 */
@AllArgsConstructor
public class LogEventListener {

    private LogHandler logHandler;

    @Async
    @Order
    @EventListener
    public void handle(LogEvent logEvent) {
        logHandler.handle((LogRecord) logEvent.getSource());
    }

}
