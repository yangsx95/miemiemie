package com.miemiemie.starter.data.protection;

import com.miemiemie.starter.data.protection.strategy.encryption.DefaultSecureEncryptionStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 安全端点标记，被标记的endpoint在请求时会解密，响应时会加密
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SecureEndpoint {

    /**
     * 请求是否解密
     *
     * @return 是否解密
     */
    boolean requestDecrypted() default true;

    /**
     * 响应是否加密
     *
     * @return 是否加密
     */
    boolean responseEncrypted() default true;

    Class<? extends EncryptionStrategy> strategy() default DefaultSecureEncryptionStrategy.class;

}
