package com.miemiemie.starter.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yangshunxiang
 * @since 2023/7/10
 */
class EqualsUtilTest {

    @Data
    @AllArgsConstructor
    static class A {

        private Long id;

        private String name;

        private Integer age;
    }

    @Data
    @AllArgsConstructor
    static class B {

        private Long bobbyId;

        private String name;

        private Integer age;
    }

    @Test
    void equalsAndGroup() {
        List<A> sourceList = new ArrayList<>();
        sourceList.add(new A(1L, "张三", 18));
        sourceList.add(new A(2L, "李四", 18));
        sourceList.add(new A(3L, "王五", null));


        List<B> targetList = new ArrayList<>();
        targetList.add(new B(1L, "张三", 18));
        targetList.add(new B(2L, "李四", null));
        targetList.add(new B(null, "赵大傻", null));


        EqualsUtil.GroupListData<A, B> group = EqualsUtil.equalsAndGroup(sourceList, targetList, A::getName, B::getName, Equator.equaling(A::getName, B::getName).thenEqualing(A::getAge, B::getAge));
        System.out.println(group);

    }
}