package com.miemiemie.starter.log;

import com.miemiemie.starter.log.entity.LogRecord;

/**
 * 日志处理器
 *
 * @author yangshunxiang
 * @since 2023/4/4
 */
@FunctionalInterface
public interface LogHandler {

    void handle(LogRecord logRecord);

}
