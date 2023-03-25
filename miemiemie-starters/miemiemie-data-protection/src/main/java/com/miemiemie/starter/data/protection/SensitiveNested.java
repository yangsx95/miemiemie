package com.miemiemie.starter.data.protection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 嵌套敏感数据标记，被标记的字段会被进行加密或者脱敏的处理
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SensitiveNested {
}
