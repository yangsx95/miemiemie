package com.miemiemie.starter.core.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * 分页实体
 *
 * @author yangshunxiang
 * @since 2022/01/25
 */
@Getter
@Setter
@ToString
public class Page<T> implements Serializable, Iterator<T> {

    public Page() {
    }

    public Page(long totalCount, long pageSize, long currentPage) {
        this(totalCount, pageSize, currentPage, null);
    }

    public Page(long totalCount, long pageSize, long currentPage, List<T> records) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        if (Objects.nonNull(records) && !records.isEmpty()) {
            this.records = records;
        }
    }

    /**
     * 总条数
     */
    private long totalCount;

    /**
     * 每页条数
     */
    private long pageSize = 10;

    /**
     * 当前页数
     */
    private long currentPage;

    /**
     * 分页数据记录
     */
    private List<T> records = new ArrayList<>();

    /**
     * 设置总页数什么也不做，由get方法生成此字段，添加此方法，主要用于json解析
     *
     * @param totalPage 总页数
     */
    public void setTotalPage(long totalPage) {
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public long getTotalPage() {
        if (getPageSize() == 0) {
            return 0L;
        }
        long pages = getTotalCount() / getPageSize();
        if (getTotalCount() % getPageSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * 转换分页对象的数据
     *
     * @param mapper 转换器
     * @param <R>    新的数据类型
     * @return 分页对象
     */
    public <R> Page<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
        Page<R> rPage = new Page<>();
        rPage.setPageSize(getPageSize());
        rPage.setCurrentPage(getCurrentPage());
        rPage.setTotalPage(getTotalPage());
        rPage.setTotalCount(getTotalCount());
        rPage.setRecords(collect);
        return rPage;
    }

    @Override
    public boolean hasNext() {
        return getRecords().iterator().hasNext();
    }

    @Override
    public T next() {
        return getRecords().iterator().next();
    }

    @Override
    public void remove() {
        getRecords().iterator().remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        getRecords().iterator().forEachRemaining(action);
    }
}
