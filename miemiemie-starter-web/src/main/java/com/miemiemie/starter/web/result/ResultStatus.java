package com.miemiemie.starter.web.result;

import com.miemiemie.core.enums.CommonEnum;

/**
 * 响应状态枚举抽象
 *
 * @author 杨顺翔
 * @since 2022/07/23
 */
public interface ResultStatus extends CommonEnum<Integer> {

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
    String getMessage();
}
