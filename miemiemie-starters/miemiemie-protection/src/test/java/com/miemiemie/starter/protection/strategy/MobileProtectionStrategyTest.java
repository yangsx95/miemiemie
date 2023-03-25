package com.miemiemie.starter.protection.strategy;

import com.miemiemie.starter.protection.BaseTest;
import com.miemiemie.starter.protection.masking.MobileMasking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MobileProtectionStrategyTest extends BaseTest {

    private final MobileMasking strategy = new MobileMasking();

    @Test
    public void protect() {
        Object protect = strategy.mask("18362459989");
        System.out.println(protect);
        Assertions.assertEquals("183****9989", protect);
    }

    @Test
    public void protectErrorMobile() {
        Object protect = strategy.mask("1836245987599");
        System.out.println(protect);
        protect = strategy.mask("1836123");
        System.out.println(protect);
    }

    @Test
    public void protectDataNotString() {
        Object object = new Object();
        Object protect = strategy.mask(object.toString());
        Assertions.assertEquals(protect, object);
    }

    @Test
    public void protectNullData() {
        Object protect = strategy.mask(null);
        Assertions.assertNull(protect);
    }

}