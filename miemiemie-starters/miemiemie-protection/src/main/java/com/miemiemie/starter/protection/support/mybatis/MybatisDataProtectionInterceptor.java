package com.miemiemie.starter.protection.support.mybatis;

import com.miemiemie.starter.protection.support.DataProtectionUtil;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author yangshunxiang
 * @since 2023/3/12
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
})
public class MybatisDataProtectionInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (!(target instanceof StatementHandler statementHandler)) {
            return invocation.proceed();
        }

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
        // 如果参数类型为ParamMap
        if (parameterObject instanceof @SuppressWarnings("rawtypes")MapperMethod.ParamMap paramMap) {

            // 支持mybatis-plus的updateById方法
            if (paramMap.containsKey("et")) {
                parameterObject = paramMap.get("et");
            }
        }

        // 处理bean中的敏感数据
        DataProtectionUtil.searchAndProtectBean(parameterObject);

        return invocation.proceed();
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
