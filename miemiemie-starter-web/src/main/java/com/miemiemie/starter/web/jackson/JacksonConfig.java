package com.miemiemie.starter.web.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.miemiemie.core.enums.CommonEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * jackson相关配置
 * 备注：SmartInitializingSingleton主要用于在IoC容器基本启动完成时进行扩展，这时非Lazy的Singleton都已被初始化完成。
 *
 * @author yangshunxiang
 * @since 2023/01/24
 */
@RequiredArgsConstructor
@Configuration
public class JacksonConfig implements SmartInitializingSingleton {

    private final ObjectMapper objectMapper;

    @Override
    public void afterSingletonsInstantiated() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(CommonEnum.class, new CommonEnumSerializer());
        simpleModule.addDeserializer(CommonEnum.class, new CommonEnumDeserializer());
        objectMapper.registerModule(simpleModule);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 默认情况下，解析json为对象时，如果json中的某个属性在对象中不存在，会引发报错
        // 使用disable禁用此特性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 设置不忽略空的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }
}
