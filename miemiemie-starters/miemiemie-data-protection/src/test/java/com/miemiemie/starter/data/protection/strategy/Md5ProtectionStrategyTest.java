package com.miemiemie.starter.data.protection.strategy;

import com.miemiemie.starter.data.protection.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author yangshunxiang
 * @since 2023/3/13
 */
public class Md5ProtectionStrategyTest extends BaseTest {

    @Resource
    private Md5ProtectionStrategy strategy;

    @Test
    public void protect() {
        Object protect = strategy.protect("320305199509020000");
        System.out.println(protect);
        Assertions.assertEquals(protect, "27e52cd6c012d192ad53aaf8043e61dc");
    }

    @Test
    public void protectNullData() {
        Object protect = strategy.protect(null);
        Assertions.assertNull(protect);
    }

}
