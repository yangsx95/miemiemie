package com.miemiemie.starter.validation.validator;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.miemiemie.starter.validation.constraints.FieldMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String[] targets;
    private Class<? extends FieldMatchStrategy> strategy;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        targets = constraintAnnotation.targets();
        strategy = constraintAnnotation.strategy();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Assert.notEmpty(targets, "@FieldMatch must specify target field");
        Assert.notNull(strategy, "@FieldMatch must specify strategy");

        List<Object> values = new ArrayList<>();
        for (String propertyName : targets) {
            Field field = ReflectUtil.getField(obj.getClass(), propertyName);
            Assert.notNull(field, "target field " + propertyName + " not exist");
            values.add(ReflectUtil.getFieldValue(obj, field));
        }

        try {
            FieldMatchStrategy fieldMatchStrategy = strategy.newInstance();
            return fieldMatchStrategy.valid(values);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
