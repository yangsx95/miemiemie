package com.miemiemie.starter.web.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.miemiemie.starter.core.enums.CommonEnum;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * jackson相关配置
 * 备注：SmartInitializingSingleton主要用于在IoC容器基本启动完成时进行扩展，这时非Lazy的Singleton都已被初始化完成。
 *
 * @author yangshunxiang
 * @since 2023/01/24
 */
@Configuration
public class JacksonConfig {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_FORMAT = "HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            // 设置不忽略null的字段
            builder.serializationInclusion(JsonInclude.Include.ALWAYS);
            builder.featuresToDisable(
                    // 默认情况下，解析json为对象时，如果json中的某个属性在对象中不存在，会引发报错；使用disable禁用此特性
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    // 时间不作为timestamp输出
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            );
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)),
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            );
            builder.deserializers(
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)),
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            );
            builder.serializerByType(CommonEnum.class, new CommonEnumSerializer());
            builder.deserializerByType(CommonEnum.class, new CommonEnumDeserializer());
        };
    }

}
