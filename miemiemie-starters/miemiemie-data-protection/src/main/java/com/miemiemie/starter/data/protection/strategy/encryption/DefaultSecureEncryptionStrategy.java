package com.miemiemie.starter.data.protection.strategy.encryption;

import com.miemiemie.starter.data.protection.EncryptionStrategy;
import org.springframework.util.Assert;

/**
 * 默认端点加密策略
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public class DefaultSecureEncryptionStrategy implements EncryptionStrategy {

    private final EncryptionStrategy encryptionStrategy;

    public DefaultSecureEncryptionStrategy(EncryptionStrategy encryptionStrategy) {
        Assert.notNull(encryptionStrategy, "encryptionStrategy must not be null");
        this.encryptionStrategy = encryptionStrategy;
    }

    @Override
    public byte[] encrypt(byte[] plain) {
        return encryptionStrategy.encrypt(plain);
    }

    @Override
    public byte[] decrypt(byte[] cipher) {
        return encryptionStrategy.decrypt(cipher);
    }
}
