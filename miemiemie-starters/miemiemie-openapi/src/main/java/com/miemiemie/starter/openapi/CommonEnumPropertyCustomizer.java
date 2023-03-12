package com.miemiemie.starter.openapi;

import com.fasterxml.jackson.databind.type.SimpleType;
import com.miemiemie.starter.core.enums.CommonEnum;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yangshunxiang
 * @since 2023/3/11
 */
@SuppressWarnings({"rawtypes"})
public class CommonEnumPropertyCustomizer implements PropertyCustomizer {
    @Override
    public Schema customize(Schema property, AnnotatedType type) {
        Type rawType = type.getType();
        if (!(rawType instanceof SimpleType)) {
            return property;
        }
        Class<?> rawClass = ((SimpleType) rawType).getRawClass();
        if (!CommonEnum.class.isAssignableFrom(rawClass) || !rawClass.isEnum()) {
            return property;
        }

        ParameterizedType commonEnumType = CommonEnumParameterCustomizer.getCommonEnumInterfaceType(rawClass);
        if (commonEnumType == null) {
            return property;
        }

        Type[] actualTypeArguments = commonEnumType.getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length != 2) {
            return property;
        }

        return CommonEnumParameterCustomizer.createCommonEnumScheme(
                (Class<?>) actualTypeArguments[0],
                CommonEnumParameterCustomizer.getEnumValues(rawClass),
                CommonEnumParameterCustomizer.getEnumDesc(rawClass)
        );
    }
}
