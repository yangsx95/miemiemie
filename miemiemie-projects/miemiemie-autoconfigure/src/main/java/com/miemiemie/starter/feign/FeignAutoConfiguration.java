package com.miemiemie.starter.feign;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Configuration
@Import({FeignClientJsonErrorDecoder.class, FeignInteralRequestInterceptor.class})
public class FeignAutoConfiguration {
}
