package com.miemiemie.starter.mybatisplus.config;

import com.miemiemie.core.enums.CommonEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <code>CommonEnum</code> 枚举类型处理
 *
 * @author yangshunxiang
 * @see com.miemiemie.core.enums.CommonEnum
 * @since 2022/12/8
 */
public class CommonEnumTypeHandler<E extends Enum<E> & CommonEnum<?, ?>> extends BaseTypeHandler<E> {

    private final Class<E> enumClassType;

    private final Class<?> enumCodeClassType;

    public CommonEnumTypeHandler(Class<E> type) {
        this.enumClassType = type;
        try {
            Method getCodeMethod = type.getMethod(CommonEnum.METHOD_NAME_CODE_GETTER);
            enumCodeClassType = getCodeMethod.getReturnType();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            return;
        }
        ps.setObject(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getCommonEnumByCode(rs.getObject(columnName, enumCodeClassType), enumClassType);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getCommonEnumByCode(rs.getObject(columnIndex, enumCodeClassType), enumClassType);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getCommonEnumByCode(cs.getObject(columnIndex, enumCodeClassType), enumClassType);
    }

    private E getCommonEnumByCode(Object code, Class<E> enumClass) {
        for (E item : enumClass.getEnumConstants()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
