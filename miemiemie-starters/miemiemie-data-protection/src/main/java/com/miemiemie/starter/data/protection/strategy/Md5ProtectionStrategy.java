package com.miemiemie.starter.data.protection.strategy;

import cn.hutool.crypto.digest.MD5;
import com.miemiemie.starter.data.protection.ProtectionStrategy;

import java.util.Optional;

/**
 * md5签名算法策略
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public class Md5ProtectionStrategy implements ProtectionStrategy {

    private final String salt;

    public Md5ProtectionStrategy(String salt) {
        this.salt = salt;
    }

    @Override
    public Object protect(Object data) {
        if (data == null) {
            return null;
        }

        return MD5.create().digestHex(Optional.ofNullable(salt).orElse("") + data);
    }

}
