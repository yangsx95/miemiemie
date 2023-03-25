package com.miemiemie.starter.data.protection.support.web;

import com.miemiemie.starter.data.protection.support.DataProtectionUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 数据保护响应体处理
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
@ControllerAdvice
public class DataProtectionResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断返回值类型是否为对象类型
        return !returnType.getGenericParameterType().equals(String.class);
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // 判断响应数据类型是否为对象类型
        if (body == null) {
            return null;
        }

        // 处理bean中的敏感数据
        DataProtectionUtil.searchAndProtectBean(body);

        return body;
    }
}
