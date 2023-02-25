package com.miemiemie.core.page.entity;

import lombok.Data;

import java.util.List;

@Data
public class MybatisPage<T> {

    private int pageSize;

    private int current;

    private long total;

    private int page;

    private List<T> data;
}
