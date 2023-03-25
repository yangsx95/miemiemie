package com.miemiemie.starter.protection.support.mybatis;

import com.miemiemie.starter.protection.BaseTest;
import org.junit.jupiter.api.*;

import javax.annotation.Resource;

/**
 * @author yangshunxiang
 * @since 2023/3/24
 */
public class DataProtectionMybatisTest extends BaseTest {

    @Resource
    private PersonMapper personMapper;

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setPname("yangshunxiang");
        person.setGender(1);
        person.setBelief(1);
        person.setCardNo("123456789012345678");
        personMapper.insert(person);
    }

    @AfterEach
    public void tearDown() {
        personMapper.deleteById(person.getId());
    }

    @Test
    @Order(1)
    public void testInsert() {
        Assertions.assertEquals("123456********5678", person.getCardNo());
    }

    @Test
    @Order(2)
    public void testUpdate() {
        person.setCardNo("11111111111111111");
        personMapper.updateById(person);
        Assertions.assertEquals("111**********1111", person.getCardNo());
    }

}
