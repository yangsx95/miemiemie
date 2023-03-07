package com.miemiemie.validation.annotation;


import com.miemiemie.validation.validator.FieldMatchStrategy;
import com.miemiemie.validation.validator.FieldMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented
@Repeatable(FieldMatch.List.class)
public @interface FieldMatch {

    String message() default "{constraints.fieldmatch}";

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
