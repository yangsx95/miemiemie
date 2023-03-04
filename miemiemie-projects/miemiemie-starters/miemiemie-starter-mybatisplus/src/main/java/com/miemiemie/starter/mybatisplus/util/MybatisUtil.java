package com.miemiemie.starter.mybatisplus.util;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Assert;

import javax.xml.ws.Holder;
import java.util.Objects;

import static com.baomidou.mybatisplus.core.metadata.TableInfoHelper.getTableInfo;

public class MybatisUtil {

    public static TableInfo getAndSetTableInfoFromHolder(Holder<TableInfo> tableInfoHolder, Object entity) {
        if (Objects.isNull(tableInfoHolder.value)) {
            TableInfo tableInfo = getTableInfo(entity.getClass());
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
}
