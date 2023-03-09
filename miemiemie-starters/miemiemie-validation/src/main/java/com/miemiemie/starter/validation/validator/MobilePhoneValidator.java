package com.miemiemie.starter.validation.validator;

import com.miemiemie.starter.validation.constraints.MobilePhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验
 *
 * @author yangshunxiang
 * @see MobilePhone
 * @since 2023/01/27
 */
public class MobilePhoneValidator implements ConstraintValidator<MobilePhone, String> {

    private MobilePhoneRegularExp regionCode;

    @Override
    public void initialize(MobilePhone constraintAnnotation) {
        this.regionCode = constraintAnnotation.regionCode();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        return regionCode.validate(s);
    }

}
