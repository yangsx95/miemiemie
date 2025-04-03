package com.miemiemie.starter.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miemiemie.starter.mybatisplus.entity.Person;
import com.miemiemie.starter.mybatisplus.enums.Belief;
import com.miemiemie.starter.mybatisplus.enums.Gender;
import com.miemiemie.starter.mybatisplus.mapper.PersonMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = RootMapperTest.class)
@EnableAutoConfiguration
public class RootMapperTest{

    @Resource
    private PersonMapper personMapper;

    @Test
    public void testInsert() {
        Person p = new Person();
        p.setPname("嘎嘎");
        personMapper.insert(p);
    }

    @Test
    public void testUpdate() {
        Person p = personMapper.selectOne(Wrappers.lambdaQuery(Person.class).eq(Person::getPname, "嘎嘎"));
        p.setBelief(Belief.CHRISTIANITY);
        personMapper.updateById(p);
    }

    @Test
    public void testInsertBatch() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setBelief(Belief.BUDDHISM);
            p.setGender(Gender.WOMAN);
            p.setPname("李" + i);
            list.add(p);
        }
        int count = personMapper.insertBatch(list);
        System.out.println("共插入了" + count + "数据");
    }

    @Test
    public void testSelectListToMap() {
        Map<Long, Person> personMap = personMapper.selectListToMap(Wrappers.lambdaQuery(Person.class), Person::getId);
        System.out.println(personMap);
    }

    @Test
    public void testUpdateBatchById() {
        List<Person> people = personMapper.selectList(Wrappers.lambdaQuery());
        people.forEach(e -> {e.setGender(Gender.MAN); e.setUpdateTime(LocalDateTime.now());});
        personMapper.updateBatchById(people);
    }

}
