package com.miemiemie.starter.core.page;

import com.miemiemie.starter.core.page.entity.MybatisPage;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MybatisPageConvert implements PageConvert<MybatisPage<?>> {


    @SuppressWarnings("unchecked")
    @Override
    public <T> Page<T> convert(Object page) {
        Assert.isInstanceOf(MybatisPage.class, page, "just convert MybaitsPage Class");
        MybatisPage<T> mybatisPage = (MybatisPage<T>) page;
        Page<T> result = new Page<>();
        result.setTotalPage(mybatisPage.getPage());
        result.setPageSize(mybatisPage.getPageSize());
        result.setCurrentPage(mybatisPage.getCurrent());
        result.setTotalCount(mybatisPage.getTotal());
        result.setRecords(mybatisPage.getData());
        return result;
    }
}
