package com.miemiemie.starter.core.lang;

/**
 * @author 杨顺翔
 * @since 2025/3/30
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);

}
