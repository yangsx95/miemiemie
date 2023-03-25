package com.miemiemie.starter.protection.signature;

/**
 * 签名验签接口
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public interface Signer {

    /**
     * 对数据进行签名
     *
     * @param data 需要签名的数据
     * @param key  用于签名的密钥
     * @return 签名后的数据
     */
    byte[] sign(byte[] data, byte[] key);

    /**
     * 验证数据的签名是否正确
     *
     * @param data      需要验证的数据
     * @param signature 签名数据
     * @param key       用于签名的密钥
     * @return 是否验证通过
     */
    boolean verify(byte[] data, byte[] signature, byte[] key);
}