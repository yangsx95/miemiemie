package com.miemiemie.starter.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.miemiemie.starter.mybatisplus.entity.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * mybatis plus 字段自动填充
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(getFieldValByName(BaseEntity.Fields.createName, metaObject))) {
            setFieldValByName(BaseEntity.Fields.createName, now, metaObject);
        }
        if (Objects.isNull(getFieldValByName(BaseEntity.Fields.updateTime, metaObject))) {
            setFieldValByName(BaseEntity.Fields.updateTime, now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.isNull(getFieldValByName(BaseEntity.Fields.updateTime, metaObject))) {
            setFieldValByName(BaseEntity.Fields.updateTime, LocalDateTime.now(), metaObject);
        }
    }
}
