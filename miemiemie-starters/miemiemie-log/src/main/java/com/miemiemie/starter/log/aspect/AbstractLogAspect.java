package com.miemiemie.starter.log.aspect;

import com.miemiemie.starter.core.lang.CheckedRunnable;
import com.miemiemie.starter.log.entity.LogRecord;
import com.miemiemie.starter.log.event.LogEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * @author yangshunxiang
 * @since 2023/4/30
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractLogAspect {

    private static final ThreadLocal<LogRecord> LOG_RECORD_THREAD_LOCAL = new ThreadLocal<>();

    protected final ApplicationContext applicationContext;

    protected void tryCatch(CheckedRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("LogHandler handle error", e);
        }
    }

    protected void publishLogEvent(LogRecord logRecord) {
        applicationContext.publishEvent(new LogEvent(logRecord));
        LOG_RECORD_THREAD_LOCAL.remove();
    }

    protected void setLogRecord(LogRecord logRecord) {
        LOG_RECORD_THREAD_LOCAL.set(logRecord);
    }

    protected LogRecord getLogRecord() {
        LogRecord logRecord = LOG_RECORD_THREAD_LOCAL.get();
        if (Objects.isNull(logRecord)) {
            return new LogRecord();
        }
        return logRecord;
    }

}
