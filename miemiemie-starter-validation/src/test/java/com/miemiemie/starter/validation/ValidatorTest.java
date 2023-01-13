package com.miemiemie.starter.validation;

import com.miemiemie.starter.validation.vo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author yangshunxiang
 * @since  2023/1/13
 */
@SpringBootTest(classes = ValidatorTest.class)
@EnableAutoConfiguration
public class ValidatorTest {

    @Resource
    private Validator validator;

    @Test
    public void testValidator() {
        Person p = new Person();
        Set<ConstraintViolation<Person>> validate = validator.validate(p);
        // 默认快速校验，只会返回一个提示
        System.out.println(validate);
    }

    @Test
    public void testValidator4CommonEnum() {
        Person p = new Person();
        p.setName("张三");
        p.setGender(2);
        Set<ConstraintViolation<Person>> validate = validator.validate(p);
        // 默认快速校验，只会返回一个提示
        System.out.println(validate);
    }
}
