package com.miemiemie.starter.protection.strategy;

import com.miemiemie.starter.protection.BaseTest;
import com.miemiemie.starter.protection.encryption.Md5Encryptor;

import javax.annotation.Resource;

/**
 * @author yangshunxiang
 * @since 2023/3/13
 */
public class Md5EncryptionStrategyTest extends BaseTest {

    @Resource
    private Md5Encryptor strategy;

//    @Test
//    public void protect() {
//        Object protect = strategy.mask("320305199509020000");
//        System.out.println(protect);
//        Assertions.assertEquals(protect, "27e52cd6c012d192ad53aaf8043e61dc");
//    }
//
//    @Test
//    public void protectNullData() {
//        Object protect = strategy.mask(null);
//        Assertions.assertNull(protect);
//    }

}
