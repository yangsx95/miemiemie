package com.miemiemie.validation.validator;


import com.miemiemie.validation.annotation.FieldMatch;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

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
            Field field = ReflectionUtils.findField(obj.getClass(), propertyName);
            Assert.notNull(field, "target field " + propertyName + " not exist");
            values.add(ReflectionUtils.getField(field, values));
        }

        try {
            FieldMatchStrategy fieldMatchStrategy = strategy.newInstance();
            return fieldMatchStrategy.valid(values);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
