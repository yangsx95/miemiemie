package com.miemiemie.starter.mybatisplus.config;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * 拦截 mybatis sql 打印过程，将参数拼接完整sql打印
 *
 * @author yangshunxiang
 * @since 2023/1/18
 */
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})
})
public class MyBatisLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        PreparedStatement statement = (PreparedStatement) invocation.getArgs()[0];
        PreparedStatement sqlStatement = null;
        //等待方法结果出来
        Object result = invocation.proceed();

        if (Proxy.isProxyClass(statement.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(statement);
            if (handler.getClass().getName().endsWith(".PreparedStatementLogger")) {
                Field field = handler.getClass().getDeclaredField("statement");
                field.setAccessible(true);
                sqlStatement = (PreparedStatement) field.get(handler);
            }
        }
        assert sqlStatement != null;
        System.out.println("=================================================== execute sql =============================================================================");
        System.out.println(sqlStatement.toString().substring(sqlStatement.toString().indexOf(":") + 1));
        System.out.println("=================================================== execute sql =============================================================================");

        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}