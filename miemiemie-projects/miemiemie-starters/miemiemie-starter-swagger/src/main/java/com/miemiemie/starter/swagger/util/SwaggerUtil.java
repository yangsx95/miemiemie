package com.miemiemie.starter.swagger.util;

import com.miemiemie.core.enums.CommonEnum;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import springfox.documentation.service.AllowableListValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SwaggerUtil {

    private SwaggerUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static List<String> commonEnumConstantsDescList(Class<?> enumClass) {
        Object[] enumConstants = enumClass.getEnumConstants();
        return Arrays.stream(enumConstants)
                .filter(Objects::nonNull)
                .map(item -> ((CommonEnum<?, ?>) item).getCode() + ":" + ((CommonEnum<?, ?>) item).getMessage())
                .collect(Collectors.toList());
    }

    public static AllowableListValues commonEnumConstantAvailable(Class<?> enumClass) {
        Object[] enumConstants = enumClass.getEnumConstants();
        List<String> values = Arrays.stream(enumConstants)
                .filter(Objects::nonNull)
                .map(item -> ((CommonEnum<?, ?>) item).getCode())
                .map(String::valueOf)
                .collect(Collectors.toList());
        Method getCode = ReflectionUtils.findMethod(enumClass, "getCode");
        Assert.notNull(getCode, "CommonEnum no getCode Method");

        return new AllowableListValues(values, "LIST" );
    }

    public static Object getFiledVale(Object targetObject, String filedName) {
        Field descField = ReflectionUtils.findField(targetObject.getClass(), filedName);
        Assert.notNull(descField, "No " + filedName + " field");
        ReflectionUtils.makeAccessible(descField);
        return ReflectionUtils.getField(descField, targetObject);
    }

}
