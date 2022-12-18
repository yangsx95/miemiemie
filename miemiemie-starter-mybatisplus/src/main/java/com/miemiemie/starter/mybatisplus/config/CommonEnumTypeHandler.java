package com.miemiemie.starter.mybatisplus.config;

import com.miemiemie.core.enums.CommonEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

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
@SuppressWarnings("unchecked")
public class CommonEnumTypeHandler<E extends Enum<E> & CommonEnum<?, ?>> extends BaseTypeHandler<E> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            return;
        }
        ps.setObject(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getCommonEnumByCode(rs.getObject(columnName), (Class<E>) this.getRawType());
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getCommonEnumByCode(rs.getObject(columnIndex), (Class<E>) this.getRawType());
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getCommonEnumByCode(cs.getObject(columnIndex), (Class<E>) this.getRawType());
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
