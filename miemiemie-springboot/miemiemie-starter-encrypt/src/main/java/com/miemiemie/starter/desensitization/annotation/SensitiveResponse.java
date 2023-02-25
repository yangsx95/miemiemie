package com.miemiemie.starter.desensitization.annotation;

import java.lang.annotation.*;

/**
 * 敏感内容
 *
 * @author yangshunxiang
 * @since 2023/1/31
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
public @interface SensitiveResponse {
}
