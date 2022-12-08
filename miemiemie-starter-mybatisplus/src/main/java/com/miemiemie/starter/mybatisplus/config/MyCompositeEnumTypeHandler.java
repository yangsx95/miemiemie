package com.miemiemie.starter.mybatisplus.config;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.miemiemie.core.enums.CommonEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 枚举类型处理
 */
public class MyCompositeEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private Class<E> enumClassType;

    private final boolean isCommonEnum;

    private final Method getCodeMethod;

    private final Class<?> getCodeMethodReturnType;

    public MyCompositeEnumTypeHandler(Class<E> enumClassType) {
        this.enumClassType = enumClassType;
        List<TypeVariable<? extends Class<?>>> commonEnumInterfaceList = Arrays.stream(enumClassType.getInterfaces())
                .filter(e -> e.isInstance(CommonEnum.class))
                .map(Class::getTypeParameters)
                .map(t -> t[0])
                .collect(Collectors.toList());
        if (commonEnumInterfaceList.isEmpty()) {
            isCommonEnum = false;
            getCodeMethod = null;
            getCodeMethodReturnType = null;
        } else {
            isCommonEnum = true;
            getCodeMethod = ReflectionUtils.getUniqueDeclaredMethods(
                    enumClassType,
                    method -> StringUtils.equals(method.getName(), "getCode") && method.getParameterCount() == 0
            )[0];
            getCodeMethodReturnType = getCodeMethod.getReturnType();
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (isCommonEnum) {
            ps.setObject(i, getCode(parameter));
        } else {

        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (isCommonEnum) {
            Object value = rs.getObject(columnName, this.getCodeMethodReturnType);
            if (null == value && rs.wasNull()) {
                return null;
            }
            return CommonEnum.getEnum(value, enumClassType);
        } else {
            return null;
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (isCommonEnum) {
            return null;
        } else {
            return null;
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (isCommonEnum) {
            return null;
        } else {
            return super.getNullableResult(cs, columnIndex);
        }
    }


    private Object getCode(E parameter) {
        try {
            return getCodeMethod.invoke(parameter);
        } catch (ReflectiveOperationException e) {
            throw ExceptionUtils.mpe(e);
        }
    }
}
