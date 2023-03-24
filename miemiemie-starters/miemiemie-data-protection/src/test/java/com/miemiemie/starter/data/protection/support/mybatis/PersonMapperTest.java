package com.miemiemie.starter.data.protection.support.mybatis;

import com.miemiemie.starter.data.protection.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author yangshunxiang
 * @since 2023/3/24
 */
public class PersonMapperTest extends BaseTest {

    @Resource
    private PersonMapper personMapper;

    @Test
    public void testInsert() {
        Person person = new Person();
        person.setPname("yangshunxiang");
        person.setGender(1);
        person.setBelief(1);
        person.setCardNo("123456789012345678");
        personMapper.insert(person);

        person = personMapper.selectById(person.getId());
        Assertions.assertEquals("123456********5678", person.getCardNo());
    }

}
