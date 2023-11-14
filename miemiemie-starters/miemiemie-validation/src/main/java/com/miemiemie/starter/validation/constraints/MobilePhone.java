package com.miemiemie.starter.validation.constraints;

import com.miemiemie.starter.validation.validator.MobilePhoneRegularExp;
import com.miemiemie.starter.validation.validator.MobilePhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 手机号码校验
 *
 * @author yangshunxiang
 * @since 2023/03/7
 */
@Documented
@Constraint(validatedBy = MobilePhoneValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MobilePhone {

    MobilePhoneRegularExp regionCode() default MobilePhoneRegularExp.CN;

    String message() default "{com.miemiemie.validation.constraints.Mobile.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
