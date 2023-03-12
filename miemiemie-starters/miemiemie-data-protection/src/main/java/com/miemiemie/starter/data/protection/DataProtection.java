package com.miemiemie.starter.data.protection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据保护策略标记，加到对应的字段上可以实现数据保护
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataProtection {

    /**
     * 数据保护策略，可以是脱敏，也可以是加密
     *
     * @return 保护策略
     */
    Class<? extends ProtectionStrategy> strategy();

}
