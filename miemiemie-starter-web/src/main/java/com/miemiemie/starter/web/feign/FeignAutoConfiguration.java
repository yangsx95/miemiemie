package com.miemiemie.starter.web.feign;

import com.miemiemie.starter.web.contstants.InnerHttpHeaders;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignAutoConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // API 调用增加请求头
        template.header(InnerHttpHeaders.Fields._MMM_SERVICE_TYPE, InnerHttpHeaders.ServiceTypeEnum.SERVICE.getCode());
    }
}
