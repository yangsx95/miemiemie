package com.miemiemie.starter.web.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.miemiemie.core.enums.CommonEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Configuration;

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
    }
}
