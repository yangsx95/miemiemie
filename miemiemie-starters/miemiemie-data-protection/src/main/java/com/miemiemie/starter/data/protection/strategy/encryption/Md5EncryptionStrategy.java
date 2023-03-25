package com.miemiemie.starter.data.protection.strategy.encryption;

import cn.hutool.crypto.digest.MD5;
import com.miemiemie.starter.data.protection.EncryptionStrategy;
import com.miemiemie.starter.data.protection.ProtectionStrategy;

/**
 * md5签名算法策略
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public class Md5EncryptionStrategy implements ProtectionStrategy, EncryptionStrategy {

    @Override
    public Object protect(Object data) {
        if (data == null) {
            return null;
        }

        if (data instanceof byte[]) {
            return encrypt((byte[]) data);
        }

        return decrypt(data.toString().getBytes());
    }

    @Override
    public byte[] encrypt(byte[] plain) {
        if (plain == null) {
            return null;
        }

        return MD5.create().digest(plain);
    }

    @Override
    public byte[] decrypt(byte[] cipher) {
        throw new UnsupportedOperationException("md5 does not support decrypt");
    }

}
