package com.miemiemie.starter.data.protection.strategy.encryption;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.miemiemie.starter.data.protection.EncryptionStrategy;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * AES加密策略
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public class AesEncryptionStrategy implements EncryptionStrategy {
    /**
     * 字符集
     */
    private final Charset charset;

    /**
     * 加密模式，比如ECB(Electronic codebook)：电子密码本模式，CBC(Cipher block chaining)：密码分组链接模式
     */
    private final Mode mode;

    /**
     * 填充模式，在AES加密算法下, 要求原文长度必须是16byte的整数倍，如果不是16byte的整数倍，就需要使用填充模式
     */
    private final Padding padding;

    private final byte[] key;

    /**
     * 偏移量，ECB模式不需要，CBC模式下必须为16位
     */
    private final byte[] iv;

    public AesEncryptionStrategy(Charset charset, Mode mode, Padding padding, byte[] key, byte[] iv) {
        this.charset = charset;
        this.mode = mode;
        this.padding = padding;
        this.key = key;
        this.iv = iv;
    }

    public AesEncryptionStrategy(Charset charset, Mode mode, Padding padding, byte[] key) {
        this.charset = charset;
        this.mode = mode;
        this.padding = padding;
        this.key = key;
        this.iv = null;
    }

    public AesEncryptionStrategy(Mode mode, Padding padding, byte[] key) {
        this.charset = StandardCharsets.UTF_8;
        this.mode = mode;
        this.padding = padding;
        this.key = key;
        this.iv = null;
    }

    /**
     * 加密
     *
     * @param plain 加密前的数据，可以使byte[]，如果是其他类型，将会调用toString方法转换为byte[]
     * @return 返回加密后的byte[]
     */
    @Override
    public byte[] encrypt(byte[] plain) {
        if (plain == null) {
            return null;
        }

        AES aes;
        if (mode == Mode.ECB) {
            aes = new AES(mode, padding, key);
        } else {
            aes = new AES(mode, padding, key, iv);
        }
        return aes.encrypt(plain);
    }

    @Override
    public byte[] decrypt(byte[] cipher) {
        return null;
    }
}
