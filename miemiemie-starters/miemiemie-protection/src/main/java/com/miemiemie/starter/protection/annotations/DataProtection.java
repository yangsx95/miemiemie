package com.miemiemie.starter.protection.annotations;

import com.miemiemie.starter.protection.masking.DataMasking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感数据标记，被标记的字段会被进行加密或者脱敏的处理
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
    Class<? extends DataMasking> strategy();

}
