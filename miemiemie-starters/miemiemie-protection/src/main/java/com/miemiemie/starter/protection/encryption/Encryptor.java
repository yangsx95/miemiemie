package com.miemiemie.starter.protection.encryption;

/**
 * 加密解密器
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public interface Encryptor {

    /**
     * 数据加密
     *
     * @param plain 加密前的数据
     * @return 加密后的数据
     */
    byte[] encrypt(byte[] plain);

    /**
     * 数据解密
     *
     * @param cipher 解密前的数据
     * @return 解密后的数据
     */
    byte[] decrypt(byte[] cipher);

}
