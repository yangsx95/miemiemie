package com.miemiemie.starter.feign;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.miemiemie.starter.core.exception.BizException;
import com.miemiemie.starter.core.exception.ExceptionInfo;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.miemiemie.starter.core.enums.ResultStatusEnum.INVOKE_INTERNAL_SERVICE_FAIL;

/**
 * Feign调用异常处理包装
 *
 * @author yangshunxiang
 * @since 2023/1/13
 */
@Slf4j
public class JsonErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        // 默认处理会将异常的http状态转变为 FeignException
        // 如果需要重新直接，会返回 RetryableException
        Exception exception = super.decode(methodKey, response);

        // 如果不是FeignException，直接返回处理之后的异常对象
        if (!(exception instanceof FeignException)) {
            return exception;
        }

        // 如果响应体为空
        if (((FeignException) exception).responseBody().isEmpty()) {
            return exception;
        }

        // 如果是FeignException，则对其进行处理，并抛出业务异常
        try {
            ByteBuffer responseBody = ((FeignException) exception).responseBody().get();
            String bodyText = StandardCharsets.UTF_8.newDecoder().decode(responseBody.asReadOnlyBuffer()).toString();

            // 解析响应为异常对象
            ObjectMapper objectMapper = new ObjectMapper();
            ExceptionInfo exceptionInfo = objectMapper.readValue(bodyText, ExceptionInfo.class);

            Integer code = Optional.ofNullable(exceptionInfo).map(ExceptionInfo::getCode).orElse(INVOKE_INTERNAL_SERVICE_FAIL.getCode());
            String desc = Optional.ofNullable(exceptionInfo).map(ExceptionInfo::getMessage).orElse(INVOKE_INTERNAL_SERVICE_FAIL.getMessage());
            return new BizException(code, desc);
        } catch (Exception e) {
            log.error("feign调用异常处理失败", e);
            throw new BizException(INVOKE_INTERNAL_SERVICE_FAIL);
        }
    }
}