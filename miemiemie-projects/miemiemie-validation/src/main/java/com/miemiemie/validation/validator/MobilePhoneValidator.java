package com.miemiemie.validation.validator;

import com.miemiemie.validation.constraints.MobilePhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 手机号码校验
 *
 * @author yangshunxiang
 * @see MobilePhone
 * @since 2023/01/27
 */
public class MobilePhoneValidator implements ConstraintValidator<MobilePhone, String> {

    private MobileRegularExp regionCode;

    @Override
    public void initialize(MobilePhone constraintAnnotation) {
        this.regionCode = constraintAnnotation.regionCode();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        return Pattern.compile(regionCode.getRegularExp()).matcher(s).matches();
    }

}
