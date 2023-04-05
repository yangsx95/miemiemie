package com.miemiemie.starter.log.aspect;

import cn.hutool.extra.spring.SpringUtil;
import com.miemiemie.starter.log.LogHandler;
import com.miemiemie.starter.log.LogRecord;
import com.miemiemie.starter.log.annotations.MethodLog;
import com.miemiemie.starter.log.handler.DefaultLogHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author yangshunxiang
 * @since 2023/4/4
 */
@Aspect
@Slf4j
public class MethodLogAspect {

    @Pointcut("@annotation(com.miemiemie.starter.log.annotations.MethodLog)")
    public void methodLogPointcut() {
    }

    @SuppressWarnings("unchecked")
    @Around("methodLogPointcut()")
    public Object aroundMethodLog(ProceedingJoinPoint pjp) throws Throwable {
        Throwable throwable = null;
        long startTime = System.currentTimeMillis();
        long endTime;
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            endTime = System.currentTimeMillis();
            Method method = pjp.getSignature().getDeclaringType().getMethod(pjp.getSignature().getName(), ((MethodSignature) pjp.getSignature()).getParameterTypes());
            MethodLog methodLog = method.getAnnotation(MethodLog.class);
            LogRecord logRecord = new LogRecord(pjp.getThis(),
                    pjp.getTarget().getClass(),
                    methodLog,
                    method,
                    pjp.getArgs(),
                    result,
                    throwable,
                    startTime,
                    endTime);
            Class<? extends LogHandler>[] handlers = methodLog.handlers();
            if (handlers.length == 0) {
                handlers = new Class[]{DefaultLogHandler.class};
            }
            for (Class<? extends LogHandler> handlerClass : handlers) {
                doHandler(logRecord, handlerClass);
            }
        }
        return result;
    }

    private static void doHandler(LogRecord logRecord, Class<? extends LogHandler> aClass)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // 如果spring容器中没有handler示例，则通过反射创建实例，如果没有无参构造器，则抛出异常
        LogHandler logHandler = SpringUtil.getBean(aClass);
        if (Objects.isNull(logHandler)) {
            try {
                Constructor<? extends LogHandler> noArgsConstructor = aClass.getDeclaredConstructor();
                logHandler = noArgsConstructor.newInstance();
            } catch (NoSuchMethodException e) {
                log.error("If LogHandler not in Spring IoC，then LogHandler must have no-arg constructor", e);
                throw e;
            }
        }

        logHandler.handle(logRecord);
    }
}
