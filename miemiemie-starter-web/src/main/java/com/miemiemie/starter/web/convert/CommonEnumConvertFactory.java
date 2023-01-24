package com.miemiemie.starter.web.convert;

import com.miemiemie.core.enums.CommonEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 请求头接受参数，String转换CommonEnum类型的转换器
 *
 * @author yangshunxiang
 * @since 2023/01/22
 */
@Component
public class CommonEnumConvertFactory implements ConverterFactory<String, CommonEnum<?, ?>> {

    @NonNull
    @Override
    public <T extends CommonEnum<?, ?>> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new StringToCommonEnumConverter<>(targetType);
    }

    public static class StringToCommonEnumConverter<T extends CommonEnum<?, ?>> implements Converter<String, T> {

        private final Class<T> targetType;

        public StringToCommonEnumConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(@NonNull String source) {
            if (!StringUtils.hasText(source)) {
                return null;
            }
            for (T enumConstant : targetType.getEnumConstants()) {
                if (source.equals(String.valueOf(enumConstant.getCode()))) {
                    return enumConstant;
                }
            }
            return null;
        }
    }
}
