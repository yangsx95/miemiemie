package com.miemiemie.starter.core.page;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

    /**
     * 根据内存数据（List）创建分页
     * @param allRecords 所有数据
     * @param pageSize 每页几条
     * @param currentPage 生成第几页的数据
     * @return 分页对象
     * @param <D> 数据类型
     */
    public static <D> Page<D> pageFromList(List<D> allRecords, int pageSize, int currentPage) {
        Assert.isTrue(pageSize > 0, "每页条数必须大于0");
        Assert.isTrue(currentPage > 0, "当前页数必须大于0");
        Page<D> page = new Page<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);

        if (CollectionUtils.isEmpty(allRecords)) {
            page.setTotalCount(0);
            return page;
        }

        page.setTotalCount(allRecords.size());
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allRecords.size());
        page.setRecords(allRecords.subList(startIndex, endIndex));
        return page;
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <D> Page<D> toPage(Object page, Class<D> recordClass) {
        if (Objects.isNull(page)) {
            return emptyPage();
        }
        if (page instanceof Page) {
            return (Page<D>) page;
        }
        PageConvertFactory factory = SpringUtil.getBean(PageConvertFactory.class);
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
        return page == null || page.getRecords() == null || page.getRecords().isEmpty();
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
