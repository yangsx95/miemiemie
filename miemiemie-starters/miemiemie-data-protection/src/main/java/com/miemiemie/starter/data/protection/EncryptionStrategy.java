package com.miemiemie.starter.data.protection;

/**
 * 数据加解密策略接口
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public interface EncryptionStrategy extends ProtectionStrategy {

    default Object protect(Object data) {
        if (data == null) {
            return null;
        }
        return encrypt(data.toString().getBytes());
    }

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
