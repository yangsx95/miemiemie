package com.miemiemie.starter.protection.encoding;

/**
 * 编解码
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public interface Codec {

    /**
     * 对数据进行编码
     *
     * @param data 待编码的数据
     * @return 编码后的数据
     */
    byte[] encode(byte[] data);

    /**
     * 对数据进行解码
     *
     * @param encodedData 已编码的数据
     * @return 解码后的数据
     */
    byte[] decode(byte[] encodedData);
}