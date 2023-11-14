package com.miemiemie.starter.web.config;

import com.miemiemie.starter.core.exception.BizException;
import com.miemiemie.starter.core.result.Result;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Spring Boot返回的Error信息一共有5条，分别是timestamp、status、error、path。使用此类增加一些额外的返回信息（code，desc）
 *
 * @author yangshunxiang
 * @since 2023/1/13
 */
@Component
@Primary
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Throwable error = this.getError(webRequest);
        if (error instanceof BizException bizEx) {
            errorAttributes.put(Result.Fields.code, bizEx.getErrorCode());
            errorAttributes.put(Result.Fields.message, bizEx.getErrorMsg());
        }
        return errorAttributes;
    }
}
