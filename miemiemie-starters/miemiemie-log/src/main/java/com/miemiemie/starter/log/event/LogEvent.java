package com.miemiemie.starter.log.event;

import com.miemiemie.starter.log.entity.LogRecord;
import org.springframework.context.ApplicationEvent;

/**
 * 日志事件
 *
 * @author yangshunxiang
 * @since 2023/4/30
 */
public class LogEvent extends ApplicationEvent {

    public LogEvent(LogRecord logRecord) {
        super(logRecord);
    }

}
