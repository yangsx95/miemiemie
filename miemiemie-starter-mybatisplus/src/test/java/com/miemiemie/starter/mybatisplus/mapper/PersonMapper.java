package com.miemiemie.starter.mybatisplus.mapper;

import com.miemiemie.starter.mybatisplus.entity.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends RootMapper<Person> {
}
