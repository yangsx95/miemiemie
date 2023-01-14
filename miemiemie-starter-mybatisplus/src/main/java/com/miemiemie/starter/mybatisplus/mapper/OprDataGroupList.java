package com.miemiemie.starter.mybatisplus.mapper;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据分组对象
 *
 * @param <T> 数据类型
 * @author yangshunxiang
 */
@Getter
public class OprDataGroupList<T> {

    /**
     * 需要删除的实体对象列表
     */
    private final List<T> needDelete = new ArrayList<>();

    /**
     * 需要更新的实体对象列表
     */
    private final List<T> needUpdate = new ArrayList<>();

    /**
     * 需要新增的实体对象列表
     */
    private final List<T> needAdd = new ArrayList<>();

    /**
     * 未知的脏数据
     */
    private final List<T> dirtyData = new ArrayList<>();

    /**
     * 没有发生变化的数据对象
     */
    private final List<T> notChange = new ArrayList<>();

    int updateSize() {
        return needUpdate.size();
    }

    int addSize() {
        return needAdd.size();
    }

    int deleteSize() {
        return needDelete.size();
    }

    int notChangeSize() {
        return notChange.size();
    }

    /**
     * 是否存在脏数据
     * @return true：存在脏数据
     */
    boolean hasDirtyData() {
        return !dirtyData.isEmpty();
    }
}
