package com.miemiemie.starter.mybatisplus.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miemiemie.starter.core.page.Page;
import com.miemiemie.starter.core.page.PageConvert;
import com.miemiemie.starter.core.page.Pages;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class MybatisPlusPageConvert implements PageConvert<IPage<?>> {

    @SuppressWarnings("unchecked")
    @Override
    public <T> Page<T> convert(Object page) {
        if (Objects.isNull(page)) {
            return Pages.emptyPage();
        }
        Assert.isInstanceOf(IPage.class, page, "page is not instance MybaitsPlus IPage");

        IPage<T> rPage = (IPage<T>) page;
        Page<T> result = new Page<>();
        result.setTotalCount(rPage.getTotal());
        result.setCurrentPage(rPage.getCurrent());
        result.setPageSize(rPage.getSize());
        result.setTotalPage(rPage.getPages());
        result.setRecords(rPage.getRecords());
        return result;
    }

    @Override
    public Set<Class<?>> registrySubClass() {
        return Collections.singleton(com.baomidou.mybatisplus.extension.plugins.pagination.Page.class);
    }
}
