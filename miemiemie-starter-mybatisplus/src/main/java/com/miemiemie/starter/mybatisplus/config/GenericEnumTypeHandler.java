package com.miemiemie.starter.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.miemiemie.core.enums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 通用枚举类型处理
 *
 * @author yangshunxiang
 * @since 2022/12/8
 */
public class GenericEnumTypeHandler<E extends Enum<E>> implements TypeHandler<E> {

    /**
     * 当前枚举类型所要使用的类型处理器
     */
    private final TypeHandler<E> delegate;

    public GenericEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        if (type.getEnumConstants() == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
        delegate = getTypeHandlerByEnumClazz(type);
    }

    /**
     * 根据枚举的class判断枚举所要使用的类型处理器
     *
     * @param type 枚举类型
     * @return 类型处理器
     */
    private TypeHandler<E> getTypeHandlerByEnumClazz(Class<E> type) {
        if (CommonEnum.class.isAssignableFrom(type)) {
            //noinspection rawtypes,unchecked
            return new CommonEnumTypeHandler();
        }
        // 不是自定义的枚举类型，交给mybaitsplus处理
        return new MybatisEnumTypeHandler<>(type);
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        delegate.setParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        return delegate.getResult(rs, columnName);
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        return delegate.getResult(rs, columnIndex);
    }

    @Override
    public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return delegate.getResult(cs, columnIndex);
    }

}
