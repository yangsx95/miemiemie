package com.miemiemie.starter.validation.constraints;

import com.miemiemie.starter.validation.validator.IDNumberRegularExp;
import com.miemiemie.starter.validation.validator.IDNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 各种证件号校验
 *
 * @author yangshunxiang
 * @since 2023/3/9
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IDNumberValidator.class})
public @interface IDNumber {

    String message() default "{com.miemiemie.validation.constraints.IDNumber.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 证件类型
     *
     * @return 多个类型只要有一个类型校验成功就成功
     */
    IDNumberRegularExp[] idType() default {IDNumberRegularExp.CN_ID_CARD};

}