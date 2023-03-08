package com.miemiemie.validation.validator;

import cn.hutool.core.util.StrUtil;
import com.miemiemie.validation.constraints.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码校验
 *
 * @author yangshunxiang
 * @see com.miemiemie.validation.constraints.Mobile
 * @since 2023/01/27
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isNotBlank(s)) {
            return false;
        }



        return false;
    }


}
