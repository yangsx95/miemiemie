package com.miemiemie.starter.feign;

import com.miemiemie.starter.core.constants.InnerHttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * feign请求拦截，此拦截会增加一个请求头，标志请求来自内部服务
 *
 * @author yangshunxiang
 */
@Configuration
public class FeignInteralRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // API 调用增加请求头，代表本次请求是微服务内部请求
        template.header(InnerHttpHeaders.Fields._MMM_SERVICE_TYPE, InnerHttpHeaders.ServiceTypeEnum.SERVICE.getCode());
    }

}
