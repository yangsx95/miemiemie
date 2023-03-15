package com.miemiemie.starter.data.protection.strategy;

import com.miemiemie.starter.data.protection.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MobileProtectionStrategyTest extends BaseTest {

    private final MobileProtectionStrategy strategy = new MobileProtectionStrategy();

    @Test
    public void protect() {
        Object protect = strategy.protect("18362459989");
        System.out.println(protect);
        Assertions.assertEquals("183****9989", protect);
    }

    @Test
    public void protectErrorMobile() {
        Object protect = strategy.protect("1836245987599");
        System.out.println(protect);
        protect = strategy.protect("1836123");
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