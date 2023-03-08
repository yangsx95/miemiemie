package com.miemiemie.validation.constraints;

import com.miemiemie.validation.validator.MobileRegularExp;
import com.miemiemie.validation.validator.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 手机号码校验
 *
 * @author yangshunxiang
 * @since 2023/03/7
 */
@Documented
@Constraint(validatedBy = MobileValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mobile {

    MobileRegularExp[] nationals() default {} ;

    String message() default "{com.miemiemie.validation.constraints.Mobile.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
