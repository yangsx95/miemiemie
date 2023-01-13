package com.miemiemie.core.enums;

import java.util.Optional;

/**
 * 枚举抽象接口
 *
 * @author 杨顺翔
 * @since 2022/07/31
 */
public interface CommonEnum<K, V> {

    /**
     * 枚举唯一编码
     *
     * @return 唯一编码
     */
    K getCode();

    /**
     * 枚举描述
     *
     * @return 描述信息
     */
    V getMessage();

    /**
     * 根据枚举的code获取message
     *
     * @param code      唯一编码
     * @param enumClass 枚举类型
     * @param <T>       枚举类型
     * @param <K>       枚举code类型
     * @param <V>       枚举desc类型
     * @return 枚举的描述
     */
    static <T extends Enum<T> & CommonEnum<K, V>, K, V> V getMessage(K code, Class<T> enumClass) {
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
     * @param <K>       枚举code类型
     * @param <V>       枚举desc类型
     * @return 使用Optional包装枚举的描述
     */
    static <T extends Enum<T> & CommonEnum<K, V>, K, V> Optional<V> getOptionalMessage(K code, Class<T> enumClass) {
        return Optional.ofNullable(getMessage(code, enumClass));
    }

    /**
     * 根据枚举的code获取枚举对象
     *
     * @param code      唯一编码
     * @param enumClass 枚举类型
     * @param <T>       枚举类型
     * @param <K>       枚举code类型
     * @param <V>       枚举desc类型
     * @return 枚举对象
     */
    static <T extends Enum<T> & CommonEnum<K, V> , K, V> T getEnum(K code, Class<T> enumClass) {
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
     * @param <K>       枚举code类型
     * @param <V>       枚举desc类型
     * @return 使用Optional包装的枚举对象
     */
    static <T extends Enum<T> & CommonEnum<K, V>, K, V> Optional<T> getOptionalEnum(K code, Class<T> enumClass) {
        return Optional.ofNullable(getEnum(code, enumClass));
    }

}