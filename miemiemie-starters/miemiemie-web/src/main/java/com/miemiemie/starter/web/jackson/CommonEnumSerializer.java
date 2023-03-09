package com.miemiemie.starter.web.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.miemiemie.starter.core.enums.CommonEnum;

import java.io.IOException;

/**
 * Jackson CommonEnum枚举序列化
 *
 * @author yangshunxiang
 * @since 2022/01/22
 */
public class CommonEnumSerializer extends JsonSerializer<CommonEnum<?, ?>> {
    @Override
    public void serialize(CommonEnum<?, ?> commonEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(commonEnum.getCode());
    }
}
