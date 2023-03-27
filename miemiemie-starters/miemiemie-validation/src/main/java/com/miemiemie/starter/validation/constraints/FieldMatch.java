package com.miemiemie.starter.validation.constraints;


import com.miemiemie.starter.validation.validator.FieldMatchStrategy;
import com.miemiemie.starter.validation.validator.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 多字段联合校验
 *
 * @author yangshunxiang
 * @since 2023/3/7
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented
@Repeatable(FieldMatch.List.class)
public @interface FieldMatch {

    String message() default "{com.miemiemie.validation.constraints.FieldMatch.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 要比较的字段列表
     */
    String[] targets() default {};

    /**
     * 字符串比较策略
     */
    Class<? extends FieldMatchStrategy> strategy() default FieldMatchStrategy.AllEqualStrategy.class;

    /**
     * 多个FieldMatcher
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FieldMatch[] value();
    }

}
