package com.miemiemie.starter.mybatisplus.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.miemiemie.starter.core.lang.Holder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author 杨顺翔
 */
public class MybatisUtil {

    public static TableInfo getAndSetTableInfoFromHolder(Holder<TableInfo> tableInfoHolder, Object entity) {
        if (Objects.isNull(tableInfoHolder.value)) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            tableInfoHolder.value = tableInfo;
        }
        return tableInfoHolder.value;
    }

    public static Object getEntityPrimaryKey(Holder<TableInfo> tableInfoHolder, Object entity) {
        TableInfo tableInfo = getAndSetTableInfoFromHolder(tableInfoHolder, entity);
        return tableInfo.getPropertyValue(entity, tableInfo.getKeyProperty());
    }

    public static Class<?> getEntityClassByMapper(@SuppressWarnings("rawtypes") Class<? extends BaseMapper> mapperClass) {
        if (mapperClass == null) {
            throw new NullPointerException("mapperClass is null");
        }

        Type superClass = mapperClass.getGenericSuperclass();
        if (superClass instanceof ParameterizedType parameterizedType) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 0) {
                return (Class<?>) typeArguments[0];
            }
        }
        throw new IllegalStateException("can not find superclass of " + mapperClass.getName());
    }

    public static TableInfo getTableInfoByMapperClass(@SuppressWarnings("rawtypes") Class<? extends BaseMapper> mapperClass) {
        return TableInfoHelper.getTableInfo(MybatisUtil.getEntityClassByMapper(mapperClass));
    }
}
