package com.miemiemie.starter.data.protection.strategy;

import com.miemiemie.starter.data.protection.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author yangshunxiang
 * @since 2023/3/13
 */
public class IdCardProtectionStrategyTest extends BaseTest {

    @Resource
    private IdCardProtectionStrategy strategy;

    @Test
    public void protect() {
        Object protect = strategy.protect("320305199509020000");
        System.out.println(protect);
        Assertions.assertEquals(protect, "320305********0000");
    }

    @Test
    public void protectErrorIdCard() {
        Object protect = strategy.protect("320305199509020000111111111");
        System.out.println(protect);
        protect = strategy.protect("32030519950902");
        System.out.println(protect);
    }

    @Test
    public void protectDataNotString() {
        Object object = new Object();
        Object protect = strategy.protect(object);
        Assertions.assertEquals(protect, object);
    }

    @Test
    public void protectNullData() {
        Object protect = strategy.protect(null);
        Assertions.assertNull(protect);
    }

}
