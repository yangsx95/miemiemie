package com.miemiemie.starter.web.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miemiemie.starter.core.enums.ResultStatusEnum;
import com.miemiemie.starter.core.result.Result;
import com.miemiemie.starter.web.annotation.NoPackage;
import com.miemiemie.starter.web.properties.WebProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;


/**
 * 该响应体处理将会给所有的controller接口返回包装一层Result返回
 * 除非该接口标记了 NoPackage 注解
 *
 * @author 杨顺翔
 * @see NoPackage
 * @see Result
 * @since 2022/07/30
 */
@AllArgsConstructor
@ConditionalOnWebApplication
@RestControllerAdvice(annotations = {RestController.class})
public class ResultPackageHandler implements ResponseBodyAdvice<Object> {

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    private final WebProperties webProperties;

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        String requestPath = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest()
                .getRequestURI();

        return !returnType.getDeclaringClass().isAnnotationPresent(NoPackage.class)
                && !returnType.hasMethodAnnotation(NoPackage.class)
                && webProperties.getResultPackageWhiteList().stream().noneMatch(path -> ANT_PATH_MATCHER.match(path, requestPath))
                ;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter methodParameter,
                                  @NonNull MediaType mediaType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> converterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (body instanceof Result) {
            if (!Objects.equals(ResultStatusEnum.SUCCESS.getCode(), ((Result<?>) body).getCode())) {
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return body;
        }

        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("json serialize error", e);
            }
        }

        return Result.success(body);
    }
}
