package com.miemiemie.starter.validation.validator;

import com.miemiemie.starter.validation.constraints.IDNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 各种证件号校验器，多个类型只要有一个类型校验成功就成功
 *
 * @author yangshunxiang
 * @since 2023/3/9
 */
public class IDNumberValidator implements ConstraintValidator<IDNumber, String> {

    private IDNumberRegularExp[] idType;

    @Override
    public void initialize(IDNumber annotation) {
        idType = annotation.idType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        for (IDNumberRegularExp exp : idType) {
            if (exp.validate(value)) {
                return true;
            }
        }
        return false;
    }

}
