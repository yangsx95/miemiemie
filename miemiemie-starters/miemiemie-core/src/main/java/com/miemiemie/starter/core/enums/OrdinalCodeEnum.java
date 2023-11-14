package com.miemiemie.starter.core.enums;

/**
 * 以ordinal作为code的枚举
 *
 * @author yangshunxiang
 * @since 2023/4/4
 */
public interface OrdinalCodeEnum extends CommonEnum<Integer, String> {

    @Override
    default Integer getCode() {
        return ((Enum<?>) this).ordinal();
    }
}
