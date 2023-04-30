package com.miemiemie.starter.log.aspect;

import com.miemiemie.starter.log.annotations.MethodLog;
import com.miemiemie.starter.log.entity.LogRecord;
import com.miemiemie.starter.log.enums.LogTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * @author yangshunxiang
 * @since 2023/4/4
 */
@Aspect
@Slf4j
public class MethodLogAspect extends AbstractLogAspect {

    public MethodLogAspect(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Pointcut("@annotation(com.miemiemie.starter.log.annotations.MethodLog)")
    public void methodLogPointcut() {
    }

    @Before("methodLogPointcut()")
    public void beforeMethodLog(JoinPoint joinPoint) {
        tryCatch(() -> {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            MethodLog methodLog = method.getAnnotation(MethodLog.class);
            if (methodLog == null) {
                methodLog = joinPoint.getTarget().getClass().getAnnotation(MethodLog.class);
            }

            LogRecord logRecord = new LogRecord();
            logRecord.setLogTypeEnum(LogTypeEnum.METHOD);
            logRecord.setLevel(methodLog.level());
            logRecord.setMessage(methodLog.message());
            logRecord.setStartTime(System.currentTimeMillis());
            logRecord.getMethodInfo().setMethod(method);
            logRecord.getMethodInfo().setArgs(joinPoint.getArgs());

            setLogRecord(logRecord);
        });
    }

    @AfterReturning(value = "methodLogPointcut()", returning = "returnObject")
    public void afterReturningMethodLog(Object returnObject) {
        tryCatch(() -> {
            LogRecord logRecord = getLogRecord();
            logRecord.setEndTime(System.currentTimeMillis());
            logRecord.getMethodInfo().setSuccess(true);
            logRecord.getMethodInfo().setResult(returnObject);
            publishLogEvent(logRecord);
        });
    }

    @AfterThrowing(value = "methodLogPointcut()", throwing = "throwable")
    public void afterThrowingMethodLog(Throwable throwable) {
        tryCatch(() -> {
            LogRecord logRecord = getLogRecord();
            logRecord.setEndTime(System.currentTimeMillis());
            logRecord.getMethodInfo().setSuccess(false);
            logRecord.getMethodInfo().setThrowable(throwable);

            publishLogEvent(logRecord);
        });
    }

}
