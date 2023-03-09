package com.miemiemie.starter.web.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.miemiemie.starter.core.enums.CommonEnum;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * jackson CommonEnum枚举类型返序列化处理
 *
 * @author yangshunxiang
 * @since 2022/01/22
 */
public class CommonEnumDeserializer extends JsonDeserializer<CommonEnum<?, ?>> implements ContextualDeserializer {

    private Class<? extends CommonEnum<?, ?>> propertyClass;

    public CommonEnumDeserializer() {
    }

    public CommonEnumDeserializer(Class<? extends CommonEnum<?, ?>> propertyClass) {
        this.propertyClass = propertyClass;
    }

    @Override
    public CommonEnum<?, ?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!StringUtils.hasText(jsonParser.getText())) {
            return null;
        }
        if (!CommonEnum.class.isAssignableFrom(propertyClass)) {
            return null;
        }
        if (!Enum.class.isAssignableFrom(propertyClass)) {
            return null;
        }
        if (!StringUtils.hasText(jsonParser.getText())) {
            return null;
        }
        Object[] enumConstants = propertyClass.getEnumConstants();
        for (Object enumConstant : enumConstants) {
            CommonEnum<?, ?> commonEnum = (CommonEnum<?, ?>) enumConstant;
            if (Objects.equals(commonEnum.getCode(), jsonParser.getText())) {
                return commonEnum;
            }
            if (jsonParser.getText().equals(String.valueOf(commonEnum.getCode()))) {
                return commonEnum;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) {
        return new CommonEnumDeserializer((Class<? extends CommonEnum<?, ?>>) beanProperty.getType().getRawClass());
    }
}
