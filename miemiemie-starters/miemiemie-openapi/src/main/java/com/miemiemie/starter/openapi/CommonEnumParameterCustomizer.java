package com.miemiemie.starter.openapi;

import com.miemiemie.starter.core.enums.CommonEnum;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangshunxiang
 * @since 2023/1/19
 */
public class CommonEnumParameterCustomizer implements ParameterCustomizer {

    @Override
    public Parameter customize(Parameter parameterModel, MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        if (!CommonEnum.class.isAssignableFrom(parameterType)) {
            return parameterModel;
        }

        ParameterizedType commonEnumType = getCommonEnumInterfaceType(parameterType);
        if (commonEnumType == null) {
            return parameterModel;
        }

        Type[] actualTypeArguments = commonEnumType.getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length != 2) {
            return parameterModel;
        }

        String[] enumDesc = getEnumDesc(parameterType);
        parameterModel.setSchema(
                createCommonEnumScheme((Class<?>) actualTypeArguments[0], getEnumValues(parameterType), enumDesc)
        );

        String desc = "";
        if (StringUtils.hasText(parameterModel.getDescription())) {
            desc += parameterModel.getDescription() + "\n";
        }
        desc += "枚举值描述：" + Arrays.toString(enumDesc);
        parameterModel.setDescription(desc);
        return parameterModel;
    }

    public static Schema<Object> createCommonEnumScheme(Class<?> codeClass,
                                                        List<Object> enumValues,
                                                        String[] enumDesc) {
        Schema<Object> schema = new Schema<>();
        if (Number.class.isAssignableFrom(codeClass)) {
            schema.setType("integer");
        } else if (BigDecimal.class.isAssignableFrom(codeClass)) {
            schema.setType("number");
            schema.setFormat("decimal");
        } else {
            // 其他所有类型统一使用String处理
            schema.setType("string");
        }
        schema.setEnum(enumValues);
        schema.setDescription(Arrays.toString(enumDesc));
        return schema;
    }

    /**
     * 获取一个CommonEnum枚举所实现的CommonEnum接口的参数化类型信息
     *
     * @param clazz 实现了CommonEnum枚举的class
     * @return 参数化类型信息
     */
    public static ParameterizedType getCommonEnumInterfaceType(Class<?> clazz) {
        Type[] genericTypes = clazz.getGenericInterfaces();
        List<ParameterizedType> commonEnumTypes = Arrays.stream(genericTypes)
                .filter(e -> e instanceof ParameterizedType)
                .filter(e -> ((ParameterizedType) e).getRawType() == CommonEnum.class)
                .map(e -> (ParameterizedType) e)
                .collect(Collectors.toList());
        if (commonEnumTypes.isEmpty()) {
            return null;
        }
        return commonEnumTypes.get(0);
    }

    @SuppressWarnings("rawtypes")
    public static List<Object> getEnumValues(Class<?> clazz) {
        if (!clazz.isEnum() || !CommonEnum.class.isAssignableFrom(clazz)) {
            return Collections.emptyList();
        }
        return Arrays.stream(clazz.getEnumConstants())
                .map(e -> ((CommonEnum) e).getCode())
                .collect(Collectors.toList());
    }

    @SuppressWarnings("rawtypes")
    public static String[] getEnumDesc(Class<?> clazz) {
        if (!clazz.isEnum() || !CommonEnum.class.isAssignableFrom(clazz)) {
            return new String[0];
        }
        return Arrays.stream(clazz.getEnumConstants())
                .map(e -> ((CommonEnum) e).getCode() + ":" + ((CommonEnum) e).getMessage())
                .toArray(String[]::new);
    }
}
