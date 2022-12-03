package com.miemiemie.starter.web.annotation;

import com.miemiemie.starter.web.result.Result;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解是一个标记注解，使用此注解标记的Controller将不会使用{@link Result}类型包装controller方法的返回值
 *
 * @author 杨顺翔
 * @since 2022/07/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NoPackage {
}
