package com.miemiemie.starter.web.enums;

/**
 * code 为name的Enum
 */
public interface NameCodeEnum extends CommonEnum<String> {

    @Override
    default String getCode() {
        return ((Enum<?>)this).name();
    }
}
