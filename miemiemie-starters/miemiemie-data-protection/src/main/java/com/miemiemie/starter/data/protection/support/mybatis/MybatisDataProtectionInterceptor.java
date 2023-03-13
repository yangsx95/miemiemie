package com.miemiemie.starter.data.protection.support.mybatis;

import cn.hutool.extra.spring.SpringUtil;
import com.miemiemie.starter.data.protection.DataProtection;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

import java.beans.Statement;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author yangshunxiang
 * @since 2023/3/12
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class MybatisDataProtectionInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (!(target instanceof StatementHandler)) {
            return invocation.proceed();
        }

        StatementHandler statementHandler = (StatementHandler) target;
        MetaObject metaObject = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 如果不是insert和update语句，不做处理
        if (mappedStatement.getSqlCommandType() != SqlCommandType.INSERT
                && mappedStatement.getSqlCommandType() != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }

        // 获取参数实体对象
        Object parameterObject = statementHandler.getBoundSql().getParameterObject();
        if (parameterObject == null) {
            return invocation.proceed();
        }

        // 通过实体对象类型获取他的所有字段，并找出需要保护的字段
        Field[] fields = parameterObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            DataProtection dataProtection = field.getAnnotation(DataProtection.class);
            if (dataProtection == null) {
                continue;
            }
            // 如果有注解，获取该字段原值，并做保护处理，设置为新的值
            Object value = getField(field, parameterObject);
            if (value == null) {
                continue;
            }

            value = SpringUtil.getBean(dataProtection.strategy());
            setField(field, parameterObject, value);
        }
        return invocation.proceed();
    }

    /**
     * 获取字段值
     *
     * @param field  字段
     * @param target 目标类
     * @return 字段值
     * @throws IllegalAccessException 非法访问异常
     */
    private Object getField(Field field, Object target) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object value = field.get(target);
        field.setAccessible(accessible);
        return value;
    }

    /**
     * 设置字段值
     *
     * @param field  字段
     * @param target 目标类
     * @param value  字段值
     * @throws IllegalAccessException 非法访问异常
     */
    private void setField(Field field, Object target, Object value) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(target, value);
        field.setAccessible(accessible);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // no-op
    }
}
