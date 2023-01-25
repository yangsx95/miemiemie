package com.miemiemie.core.page;

import com.miemiemie.core.util.SpringContextHolder;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 分页工具
 *
 * @author yangshunxiang
 * @since 2022/01/25
 */
public final class Pages {

    @SuppressWarnings("rawtypes")
    private static final Page EMPTY_PAGE = new Page();

    private Pages() {
        throw new AssertionError("No Pages instances for you!");
    }

    @SuppressWarnings("unchecked")
    public static <D> Page<D> emptyPage() {
        return EMPTY_PAGE;
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <D> Page<D> toPage(Object page, Class<D> recordClass) {
        if (Objects.isNull(page)) {
            return emptyPage();
        }
        if (page instanceof Page) {
            return (Page<D>) page;
        }
        PageConvertFactory factory = SpringContextHolder.getBean(PageConvertFactory.class);
        Assert.notNull(factory, "No PageConvertFactory Instance In IoC!");
        PageConvert<?> convert = factory.getConvert(page.getClass());
        Assert.notNull(convert, "No PageConvert Provider");
        return convert.convert(page);
    }

    /**
     * 判断分页对象中是否有数据
     *
     * @param page 分页对象
     * @param <D>  分页数据类型
     * @return 有数据将返回true
     */
    public static <D> boolean hasRecords(Page<D> page) {
        return page == null || page.getRecords() == null || page.getRecords().size() == 0;
    }

    /**
     * 根据分页信息判断是否有下一页数据
     *
     * @param page 分页信息
     * @param <D>  分页数据类型
     * @return 有下一页返回true
     */
    public static <D> boolean hasNextPage(Page<D> page) {
        return page != null && page.getTotalPage() > page.getCurrentPage();
    }
}
