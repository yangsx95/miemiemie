package com.miemiemie.starter.feign;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.miemiemie.starter.core.enums.CommonEnum;
import com.miemiemie.starter.core.enums.ResultStatusEnum;
import com.miemiemie.starter.core.exception.BizException;
import com.miemiemie.starter.core.result.Result;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 返回类型为Result结构的feign接口，将会解包，并将data直接映射到返回对象中
 *
 * @author yangshunxiang
 * @since 2024/1/30
 */
public class ResultDecoder implements Decoder {

    private final ObjectMapper objectMapper;

    public ResultDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (Objects.isNull(response.body())) {
            throw new DecodeException(response.status(), "没有返回的数据信息", response.request());
        }

        String bodyStr = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        // 将返回结果转换为Result对象
        Result<Object> result = parseDataFromJsonStr(bodyStr, type);
        // 如果成功，返回data
        if (CommonEnum.getEnum(result.getCode(), ResultStatusEnum.class) == ResultStatusEnum.SUCCESS) {
            return result.getData();
        }
        String errorMsg = ResultStatusEnum.INVOKE_INTERNAL_SERVICE_FAIL.getMessage();
        if (StringUtils.hasText(result.getMessage())) {
            errorMsg = errorMsg + ": " + result.getMessage();
        }
        throw new BizException(ResultStatusEnum.INVOKE_INTERNAL_SERVICE_FAIL.getCode(), errorMsg, null);
    }

    public <T> Result<T> parseDataFromJsonStr(String json, Type dataType) throws IOException {
        JavaType dataJavaType = TypeFactory.defaultInstance().constructType(dataType);
        JavaType targetType = TypeFactory.defaultInstance().constructParametricType(Result.class, dataJavaType);
        return objectMapper.readValue(json, targetType);
    }
}
