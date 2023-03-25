package com.miemiemie.starter.protection.support;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.miemiemie.starter.protection.annotations.DataProtection;
import com.miemiemie.starter.protection.annotations.DataProtectionNested;

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

        Field[] fields = ReflectUtil.getFields(parameterObject.getClass(), field -> field.isAnnotationPresent(DataProtection.class) || field.isAnnotationPresent(DataProtectionNested.class));
        for (Field field : fields) {
            // 如果当前字段含有SensitiveNested注解，优先处理内部对象保护
            DataProtectionNested dataProtectionNested = field.getAnnotation(DataProtectionNested.class);
            if (dataProtectionNested != null) {
                searchAndProtectBean(ReflectUtil.getFieldValue(parameterObject, field.getName()));
            }

            // 如果当前字段含有SensitiveData注解，对当前字段执行保护
            DataProtection dataProtection = field.getAnnotation(DataProtection.class);
            if (dataProtection == null) {
                continue;
            }

            Object value = ReflectUtil.getFieldValue(parameterObject, field.getName());
            if (value == null) {
                continue;
            }

            Object protectedValue = SpringUtil.getBean(dataProtection.strategy()).mask(value);
            ReflectUtil.setFieldValue(parameterObject, field.getName(), protectedValue);
        }
    }
}
