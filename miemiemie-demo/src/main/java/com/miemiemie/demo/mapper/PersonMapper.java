package com.miemiemie.demo.mapper;

import com.miemiemie.demo.pojo.entity.Person;
import com.miemiemie.starter.mybatisplus.mapper.RootMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PersonMapper extends RootMapper<Person> {
}
