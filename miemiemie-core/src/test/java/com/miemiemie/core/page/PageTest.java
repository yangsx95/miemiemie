package com.miemiemie.core.page;

import com.miemiemie.core.page.entity.MybatisPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest(classes = PageTest.class)
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PageTest {

    @Resource
    private PageConvertFactory pageConvertFactory;

    @Test
    public void testPageConvert() {
        PageConvert<?> convert = pageConvertFactory.getConvert(MybatisPage.class);
        MybatisPage<Object> originPage = getObjectMybatisPage();
        Page<?> page = convert.convert(originPage);
        System.out.println(page);
    }

    @Test
    public void testPagesToPage() {
        Page<String> stringPage = Pages.toPage(getObjectMybatisPage(), String.class);
        System.out.println(stringPage);
    }

    @Test
    public void testPagesEmptyPage() {
        System.out.println(Pages.emptyPage());
    }

    private static MybatisPage<Object> getObjectMybatisPage() {
        MybatisPage<Object> originPage = new MybatisPage<>();
        originPage.setPageSize(100);
        originPage.setCurrent(1);
        originPage.setTotal(1000);
        originPage.setPage(10);
        ArrayList<Object> data = new ArrayList<>();
        data.add("张三");
        data.add("李四");
        originPage.setData(data);
        return originPage;
    }
}
