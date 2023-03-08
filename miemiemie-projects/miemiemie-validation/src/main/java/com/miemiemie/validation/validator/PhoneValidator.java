package com.miemiemie.validation.validator;

import cn.hutool.core.util.StrUtil;
import com.miemiemie.validation.constraints.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码校验
 *
 * @author yangshunxiang
 * @see Phone
 * @since 2023/01/27
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isNotBlank(s)) {
            return false;
        }

        Pattern p = Pattern.compile("^1[34578][0-9]{9}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

}
