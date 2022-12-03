package com.miemiemie.starter.web.enums;

import java.util.Optional;

/**
 * 枚举抽象接口
 *
 * @author 杨顺翔
 * @since 2022/07/31
 */
public interface CommonEnum<C> {

    /**
     * 枚举唯一编码
     *
     * @return 唯一编码
     */
    C getCode();

    /**
     * 枚举描述
     *
     * @return 描述信息
     */
    String getMessage();

    /**
     * 根据枚举的code获取message
     *
     * @param code      唯一编码
     * @param enumClass 枚举类型
     * @param <T>       枚举类型
     * @param <C>       枚举code类型
     * @return 枚举的描述
     */
    static <T extends Enum<T> & CommonEnum<C>, C> String getMessage(C code, Class<T> enumClass) {
        for (T item : enumClass.getEnumConstants()) {
            if (item.getCode().equals(code)) {
                return item.getMessage();
            }
        }
        return null;
    }

    /**
     * 根据枚举的code获取message
     *
     * @param code      唯一编码
     * @param enumClass 枚举类型
     * @param <T>       枚举类型
     * @param <C>       枚举code类型
     * @return 使用Optional包装枚举的描述
     */
    static <T extends Enum<T> & CommonEnum<C>, C> Optional<String> getOptionalMessage(C code, Class<T> enumClass) {
        return Optional.ofNullable(getMessage(code, enumClass));
    }

    /**
     * 根据枚举的code获取枚举对象
     *
     * @param code      唯一编码
     * @param enumClass 枚举类型
     * @param <T>       枚举类型
     * @param <C>       枚举code类型
     * @return 枚举对象
     */
    static <T extends Enum<T> & CommonEnum<C>, C> T getEnum(C code, Class<T> enumClass) {
        for (T item : enumClass.getEnumConstants()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据枚举的code获取枚举对象
     *
     * @param code      唯一编码
     * @param enumClass 枚举类型
     * @param <T>       枚举类型
     * @param <C>       枚举code类型
     * @return 使用Optional包装的枚举对象
     */
    static <T extends Enum<T> & CommonEnum<C>, C> Optional<T> getOptionalEnum(C code, Class<T> enumClass) {
        return Optional.ofNullable(getEnum(code, enumClass));
    }

}