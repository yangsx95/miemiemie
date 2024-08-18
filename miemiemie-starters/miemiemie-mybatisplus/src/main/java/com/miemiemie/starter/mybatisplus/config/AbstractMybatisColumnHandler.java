package com.miemiemie.starter.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.miemiemie.starter.mybatisplus.entity.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author yangshunxiang
 * @since 2024/8/18
 */
public abstract class AbstractMybatisColumnHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, BaseEntity.Fields.createTime, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, BaseEntity.Fields.createBy, Long.class, getCurrentUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, BaseEntity.Fields.updateTime, LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, BaseEntity.Fields.updateBy, Long.class, getCurrentUserId());
    }

    /**
     * 获取当前的登录人id
     *
     * @return 用户id
     */
    public abstract Long getCurrentUserId();

}
