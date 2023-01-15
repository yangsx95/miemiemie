package com.miemiemie.starter.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miemiemie.starter.mybatisplus.entity.Person;
import com.miemiemie.starter.mybatisplus.enums.Belief;
import com.miemiemie.starter.mybatisplus.enums.Gender;
import com.miemiemie.starter.mybatisplus.mapper.PersonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = RootMapperTest.class)
@EnableAutoConfiguration
public class RootMapperTest{

    @Resource
    private PersonMapper personMapper;

    @Test
    public void testInsertBatch() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setBelief(Belief.BUDDHISM);
            p.setGender(Gender.WOMAN);
            p.setName("李" + i);
            list.add(p);
        }
        int count = personMapper.insertBatch(list);
        System.out.println("共插入了" + count + "数据");
    }

    @Test
    public void testMapperSelect() {
        List<Person> people = personMapper.selectList(Wrappers.lambdaQuery());
        System.out.println(people.size());
    }

}
