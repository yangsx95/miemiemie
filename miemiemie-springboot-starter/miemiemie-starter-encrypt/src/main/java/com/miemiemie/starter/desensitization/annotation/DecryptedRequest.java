package com.miemiemie.starter.desensitization.annotation;

import java.lang.annotation.*;

/**
 * 解密内容
 *
 * @author yangshunxiang
 * @since 2023/01/31
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
public @interface DecryptedRequest {

    String key() default "";

}
