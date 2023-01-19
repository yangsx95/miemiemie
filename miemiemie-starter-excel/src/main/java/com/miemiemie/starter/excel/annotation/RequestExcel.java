package com.miemiemie.starter.excel.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 请求参数是excel文件流
 *
 * @author yangshunxiang
 * @since 2023/1/19
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {

    /**
     * 文件上传时的字段名
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 文件上传时的字段名
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 是否跳过空行
     * @return 默认跳过
     */
    boolean ignoreEmptyRow() default true;

}
