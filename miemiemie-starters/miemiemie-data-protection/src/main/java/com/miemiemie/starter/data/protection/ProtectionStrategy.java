package com.miemiemie.starter.data.protection;

/**
 * 数据保护策略
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public interface ProtectionStrategy {

    /**
     * 对数据进行保护
     *
     * @param data 要保护的数据
     * @return 保护后的数据
     */
    Object protect(Object data);

    /**
     * 数据复原（前提是数据是可复原的）
     *
     * @param data 数据复原
     * @return 原数据
     */
    default Object restore(Object data) {
        return data;
    }
}
