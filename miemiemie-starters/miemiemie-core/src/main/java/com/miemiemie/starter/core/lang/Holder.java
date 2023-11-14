package com.miemiemie.starter.core.lang;

/**
 * @author yangshunxiang
 * @since 2023/3/28
 */
public class Holder<T> {

    public T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public static <T> Holder<T> of(T value) {
        return new Holder<>(value);
    }

}
