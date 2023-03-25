package com.miemiemie.starter.protection.strategy;

import com.miemiemie.starter.protection.BaseTest;
import com.miemiemie.starter.protection.masking.IdNoMasking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author yangshunxiang
 * @since 2023/3/13
 */
public class IdCardProtectionStrategyTest extends BaseTest {

    @Resource
    private IdNoMasking strategy;

    @Test
    public void protect() {
        Object protect = strategy.mask("320305199509020000");
        System.out.println(protect);
        Assertions.assertEquals(protect, "320305********0000");
    }

    @Test
    public void protectErrorIdCard() {
        Object protect = strategy.mask("320305199509020000111111111");
        System.out.println(protect);
        protect = strategy.mask("32030519950902");
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
