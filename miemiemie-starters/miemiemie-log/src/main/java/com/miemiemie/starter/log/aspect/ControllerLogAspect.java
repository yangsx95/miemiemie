package com.miemiemie.starter.log.aspect;

import com.github.f4b6a3.ulid.Ulid;
import com.miemiemie.starter.core.enums.CommonEnum;
import com.miemiemie.starter.core.enums.ResultStatusEnum;
import com.miemiemie.starter.core.result.Result;
import com.miemiemie.starter.log.LogProperties;
import com.miemiemie.starter.log.annotations.ControllerLog;
import com.miemiemie.starter.log.entity.LogRecord;
import com.miemiemie.starter.log.enums.LogTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 日志切面
 *
 * @author yangshunxiang
 * @since 2022/12/8
 */
@Aspect
@Slf4j
public class ControllerLogAspect extends AbstractLogAspect {

    private final LogProperties logProperties;

    public ControllerLogAspect(ApplicationContext applicationContext, LogProperties logProperties) {
        super(applicationContext);
        this.logProperties = logProperties;
    }

    /**
     * 所有的controller方法
     */
    @Pointcut(" @within(org.springframework.stereotype.Controller) " +
            "|| @within(org.springframework.web.bind.annotation.RestController) "
    )
    public void restLog() {
    }

    @Before("restLog()")
    public void before(JoinPoint joinPoint) {
        tryCatch(() -> {
            if (noNeedLog(joinPoint)) return;

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ControllerLog controllerLog = method.getAnnotation(ControllerLog.class);

            LogRecord logRecord = new LogRecord();
            logRecord.setStartTime(System.currentTimeMillis());
            logRecord.setLogTypeEnum(LogTypeEnum.CONTROLLER);
            logRecord.setLevel(controllerLog.level());
            logRecord.setMessage(controllerLog.message());
            logRecord.setStartTime(System.currentTimeMillis());
            logRecord.getMethodInfo().setMethod(method);
            logRecord.getMethodInfo().setArgs(joinPoint.getArgs());
            logRecord.getControllerInfo().setRequestId(Ulid.fast().toLowerCase());
            setLogRecord(logRecord);

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.isNull(attributes)) {
                return;
            }

            HttpServletRequest request = attributes.getRequest();
            logRecord.getControllerInfo().setUrl(request.getRequestURL().toString());
            logRecord.getControllerInfo().setHttpMethod(HttpMethod.valueOf(request.getMethod()));
            logRecord.getControllerInfo().setRequestIp(request.getRemoteAddr());
        });
    }

    @AfterReturning(value = "restLog()", returning = "returnObject")
    public void afterReturningMethodLog(JoinPoint joinPoint, Object returnObject) {
        tryCatch(() -> {
            if (noNeedLog(joinPoint)) return;

            LogRecord logRecord = getLogRecord();
            logRecord.setEndTime(System.currentTimeMillis());
            logRecord.getMethodInfo().setSuccess(true);
            logRecord.getMethodInfo().setResult(returnObject);

            if (returnObject instanceof Result<?>) {
                ResultStatusEnum resultStatus = CommonEnum.getEnum(((Result<?>) returnObject).getCode(), ResultStatusEnum.class);
                logRecord.getControllerInfo().setSuccess(resultStatus == ResultStatusEnum.SUCCESS);
            } else {
                logRecord.getControllerInfo().setSuccess(true);
            }
            publishLogEvent(logRecord);
        });
    }

    private boolean noNeedLog(JoinPoint joinPoint) {
        if (!logProperties.getController().isAuto()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> targetClass = joinPoint.getTarget().getClass();
            return !method.isAnnotationPresent(ControllerLog.class)
                    && !targetClass.isAnnotationPresent(ControllerLog.class);
        }
        return false;
    }

    @AfterThrowing(value = "restLog()", throwing = "throwable")
    public void afterThrowingMethodLog(Throwable throwable) {
        tryCatch(() -> {
            LogRecord logRecord = getLogRecord();
            logRecord.setEndTime(System.currentTimeMillis());
            logRecord.getMethodInfo().setSuccess(false);
            logRecord.getMethodInfo().setThrowable(throwable);
            logRecord.getControllerInfo().setSuccess(false);
            publishLogEvent(logRecord);
        });
    }
}

