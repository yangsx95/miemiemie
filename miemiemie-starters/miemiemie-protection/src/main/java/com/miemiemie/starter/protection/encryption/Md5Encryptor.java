package com.miemiemie.starter.protection.encryption;

import cn.hutool.crypto.digest.MD5;

/**
 * md5签名算法策略
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public class Md5Encryptor implements Encryptor {

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
