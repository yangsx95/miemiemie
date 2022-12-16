package com.miemiemie.starter.web.result;

import com.miemiemie.core.result.Result;
import com.miemiemie.starter.web.annotation.NoPackage;
import com.miemiemie.starter.web.contstants.InnerHttpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 该响应体处理将会给所有的controller接口返回包装一层Result返回
 * 除非该接口标记了 NoPackage 注解
 *
 * @author 杨顺翔
 * @see NoPackage
 * @see Result
 * @since 2022/07/30
 */
@ConditionalOnWebApplication
@RestControllerAdvice(annotations = {RestController.class})
public class ResultPackageHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getDeclaringClass().isAnnotationPresent(NoPackage.class)
                && !returnType.hasMethodAnnotation(NoPackage.class);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter methodParameter,
                                  @NonNull MediaType mediaType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> converterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        }

        InnerHttpHeaders innerHttpHeaders = InnerHttpHeaders.build(request.getHeaders());
        // 内部服务不包装Result
        if (innerHttpHeaders.getServiceTypeEnum() == InnerHttpHeaders.ServiceTypeEnum.SERVICE) {
            return body;
        }
        return Result.success(body);
    }
}
