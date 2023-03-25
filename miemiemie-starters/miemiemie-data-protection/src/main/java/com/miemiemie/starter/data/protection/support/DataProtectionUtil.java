package com.miemiemie.starter.data.protection.support;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.miemiemie.starter.data.protection.SensitiveData;
import com.miemiemie.starter.data.protection.SensitiveNested;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author yangshunxiang
 * @since 2023/3/25
 */
public class DataProtectionUtil {

    /**
     * 保护敏感数据
     *
     * @param parameterObject 目标对象
     */
    public static void searchAndProtectBean(Object parameterObject) {
        if (Objects.isNull(parameterObject)) {
            return;
        }

        Field[] fields = ReflectUtil.getFields(parameterObject.getClass(), field -> field.isAnnotationPresent(SensitiveData.class) || field.isAnnotationPresent(SensitiveNested.class));
        for (Field field : fields) {
            // 如果当前字段含有SensitiveNested注解，优先处理内部对象保护
            SensitiveNested sensitiveNested = field.getAnnotation(SensitiveNested.class);
            if (sensitiveNested != null) {
                searchAndProtectBean(ReflectUtil.getFieldValue(parameterObject, field.getName()));
            }

            // 如果当前字段含有SensitiveData注解，对当前字段执行保护
            SensitiveData sensitiveData = field.getAnnotation(SensitiveData.class);
            if (sensitiveData == null) {
                continue;
            }

            Object value = ReflectUtil.getFieldValue(parameterObject, field.getName());
            if (value == null) {
                continue;
            }

            Object protectedValue = SpringUtil.getBean(sensitiveData.strategy()).protect(value);
            ReflectUtil.setFieldValue(parameterObject, field.getName(), protectedValue);
        }
    }
}
