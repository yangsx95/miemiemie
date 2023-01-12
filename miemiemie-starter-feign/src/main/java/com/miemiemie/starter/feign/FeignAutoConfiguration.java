package com.miemiemie.starter.feign;

import com.miemiemie.core.constants.InnerHttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Configuration
public class FeignAutoConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // API 调用增加请求头
        template.header(InnerHttpHeaders.Fields._MMM_SERVICE_TYPE, InnerHttpHeaders.ServiceTypeEnum.SERVICE.getCode());
    }
}
