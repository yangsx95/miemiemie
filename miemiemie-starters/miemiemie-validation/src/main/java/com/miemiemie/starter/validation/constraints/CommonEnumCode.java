package com.miemiemie.starter.validation.constraints;


import com.miemiemie.starter.validation.validator.CommonEnumCodeValidatorForInteger;
import com.miemiemie.starter.validation.validator.CommonEnumCodeValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举校验注解
 *
 * @author yangshunxiang
 * @since 2023/1/13
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
// 指定关联的校验器
@Constraint(validatedBy = {CommonEnumCodeValidatorForString.class, CommonEnumCodeValidatorForInteger.class})
@Documented
public @interface CommonEnumCode {

    /**
     * 提示消息
     *
     * @return 提示消息
     */
    String message() default "{com.miemiemie.validation.constraints.CommonEnumCode.message}";

    /**
     * 对应的枚举类
     *
     * @return 枚举class
     */
    Class<?> target();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
