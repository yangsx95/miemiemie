package com.miemiemie.core.enums;

/**
 * 响应状态枚举抽象
 *
 * @author 杨顺翔
 * @since 2022/07/23
 */
public interface ResultStatus extends CommonEnum<Integer, String> {

    /**
     * 响应码
     *
     * @return 响应码
     */
    Integer getCode();

    /**
     * 响应描述
     *
     * @return 响应描述
     */
    String getDesc();
}
