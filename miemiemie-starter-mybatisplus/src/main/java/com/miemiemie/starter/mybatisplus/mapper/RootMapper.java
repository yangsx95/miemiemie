package com.miemiemie.starter.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface RootMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入实体
     * @param entityList 实体列表
     * @return 插入的条数
     */
    int insertBatch(Collection<T> entityList);
}
