package com.miemiemie.starter.data.protection.support.web;

import com.miemiemie.starter.core.enums.ResultStatusEnum;
import com.miemiemie.starter.core.exception.BizException;
import com.miemiemie.starter.data.protection.DataProtection;
import com.miemiemie.starter.data.protection.ProtectionStrategy;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // 获取所有字段
        List<Field> fields = getAllFields(body.getClass());

        // 对含有@DataProtection注解的字段进行数据保护处理
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DataProtection.class)) {
                continue;
            }
            DataProtection dataProtection = field.getAnnotation(DataProtection.class);
            ProtectionStrategy strategy;
            try {
                strategy = dataProtection.strategy().newInstance();
                field.setAccessible(true);
                Object value = field.get(body);
                field.set(body, strategy.protect(value));
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BizException(ResultStatusEnum.SERVER_ERROR.getCode(), "数据保护处理失败", e);
            }
        }
        return body;
    }

    /**
     * 获取类所有字段，包括父类字段
     */
    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }
        return fields;
    }
}
