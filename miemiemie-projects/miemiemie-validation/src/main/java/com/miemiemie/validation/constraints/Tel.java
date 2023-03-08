package com.miemiemie.validation.constraints;

import com.miemiemie.validation.validator.TelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 座机号码校验
 *
 * @author yangshunxiang
 * @since 2023/3/7
 */
@Documented
@Constraint(validatedBy = TelValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tel {

    String message() default "{com.miemiemie.validation.constraints.Tel.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
