package com.miemiemie.core.enums;

/**
 * code 为name的Enum
 */
public interface NameCodeEnum extends CommonEnum<String, String> {

    @Override
    default String getCode() {
        return ((Enum<?>)this).name();
    }
}
