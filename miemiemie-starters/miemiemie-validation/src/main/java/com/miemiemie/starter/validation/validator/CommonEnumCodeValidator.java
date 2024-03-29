package com.miemiemie.starter.validation.validator;


import com.miemiemie.starter.core.enums.CommonEnum;
import com.miemiemie.starter.validation.constraints.CommonEnumCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotEmpty;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 注解校验器
 *
 * @author yangshunxiang
 * @since 2023/1/13
 */
public class CommonEnumCodeValidator<C> implements ConstraintValidator<CommonEnumCode, C> {

    /**
     * 枚举类
     */
    @NotEmpty
    Class<?> targetEnumClass;

    @Override
    public void initialize(CommonEnumCode constraintAnnotation) {
        targetEnumClass = constraintAnnotation.target();
    }

    @Override
    public boolean isValid(C value, ConstraintValidatorContext context) {
        // 如果值为空，不做枚举校验
        if (Objects.isNull(value)) {
            return true;
        }

        // 未指定枚举类型不做校验
        if (Objects.isNull(targetEnumClass)) {
            return true;
        }

        // 指定的类型不是枚举类型报错
        if (!targetEnumClass.isEnum()) {
            throw new IllegalArgumentException("指定的target属性必须是枚举类型");
        }

        // 指定的类型必须是CommonEnum
        if (!(CommonEnum.class.isAssignableFrom(targetEnumClass))) {
            throw new IllegalArgumentException("枚举类型必须是CommonEnum");
        }

        List<? extends CommonEnum<?, ?>> commonEnumList = Arrays.stream(targetEnumClass.getEnumConstants())
                .map(e -> (CommonEnum<?, ?>) e)
                .filter(e -> Objects.equals(e.getCode(), context))
                .toList();

        return !commonEnumList.isEmpty();
    }
}
