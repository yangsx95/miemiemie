package com.miemiemie.starter.log.handler;

import com.miemiemie.starter.log.LogHandler;
import com.miemiemie.starter.log.LogRecord;
import com.miemiemie.starter.log.annotations.MethodLog;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author yangshunxiang
 * @since 2023/4/4
 */
@Slf4j
public class Slf4jLogHandler implements LogHandler {

    @Override
    public void handle(LogRecord logRecord) {
        Throwable throwable = logRecord.getThrowable();
        long cost = logRecord.getEndTime() - logRecord.getStartTime();
        if (throwable != null) {
            log.error("调用方法:{}，参数:{}，发生异常。总耗时: {}", logRecord.getMethod(), Arrays.toString(logRecord.getArgs()), cost, throwable);
            return;
        }
        MethodLog methodLogAnno = logRecord.getMethodLog();
        switch (methodLogAnno.level()) {
            case DEBUG ->
                    log.debug("调用方法:{}，参数:{}，返回: {}。总耗时: {}", logRecord.getMethod(), Arrays.toString(logRecord.getArgs()), cost, logRecord.getResult());
            case WARN ->
                    log.warn("调用方法:{}，参数:{}，返回: {}。总耗时: {}", logRecord.getMethod(), Arrays.toString(logRecord.getArgs()), cost, logRecord.getResult());
            case ERROR ->
                    log.error("调用方法:{}，参数:{}，返回: {}。总耗时: {}", logRecord.getMethod(), Arrays.toString(logRecord.getArgs()), cost, logRecord.getResult());
            default ->
                    log.info("调用方法:{}，参数:{}，返回: {}。总耗时: {}", logRecord.getMethod(), Arrays.toString(logRecord.getArgs()), cost, logRecord.getResult());

        }
    }
}
