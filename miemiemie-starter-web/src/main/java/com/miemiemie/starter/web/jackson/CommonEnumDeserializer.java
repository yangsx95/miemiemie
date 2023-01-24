package com.miemiemie.starter.web.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.miemiemie.core.enums.CommonEnum;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * jackson CommonEnum枚举类型返序列化处理
 *
 * @author yangshunxiang
 * @since 2022/01/22
 */
public class CommonEnumDeserializer extends JsonDeserializer<CommonEnum<?, ?>> implements ContextualDeserializer {

    @Setter
    private Class<?> target;

    @Override
    public CommonEnum<?, ?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!StringUtils.hasText(jsonParser.getText())) {
            return null;
        }
        if (!CommonEnum.class.isAssignableFrom(target)) {
            return null;
        }
        if (!Enum.class.isAssignableFrom(target)) {
            return null;
        }
        if (!StringUtils.hasText(jsonParser.getText())) {
            return null;
        }
        Object[] enumConstants = target.getEnumConstants();
        for (Object enumConstant : enumConstants) {
            CommonEnum<?, ?> commonEnum = (CommonEnum<?, ?>) enumConstant;
            if (jsonParser.getText().equals(commonEnum.getCode())) {
                return commonEnum;
            }
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext,
                                                BeanProperty beanProperty) {
        Class<?> rawCls = deserializationContext.getContextualType().getRawClass();
        CommonEnumDeserializer enumDeserializer = new CommonEnumDeserializer();
        enumDeserializer.setTarget(rawCls);
        return enumDeserializer;
    }
}
