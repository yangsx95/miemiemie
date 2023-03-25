package com.miemiemie.starter.protection.masking;

/**
 * 数据脱敏处理
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public interface DataMasking<S, T> {

    /**
     * 对数据进行脱敏处理
     *
     * @param data 待脱敏的数据
     * @return 脱敏后的数据
     */
    T mask(S data);

}
