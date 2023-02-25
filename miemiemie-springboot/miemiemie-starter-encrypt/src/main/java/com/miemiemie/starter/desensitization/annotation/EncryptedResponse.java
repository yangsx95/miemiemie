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
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
public @interface EncryptedResponse {

    String key() default "";

}
