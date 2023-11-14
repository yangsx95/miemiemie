package com.miemiemie.starter.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.miemiemie.starter.core.lang.Holder;
import com.miemiemie.starter.core.page.Page;
import com.miemiemie.starter.core.page.Pages;
import com.miemiemie.starter.mybatisplus.util.MybatisUtil;
import org.apache.ibatis.annotations.Param;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface RootMapper<T> extends BaseMapper<T> {

    /**
     * 重写selectOne，内部使用selectList替换，防止引发selectOne查询出多个结果报错
     *
     * @param queryWrapper 查询条件
     * @return 查询结果
     */
    @Override
    default T selectOne(Wrapper<T> queryWrapper) {
        if (Objects.nonNull(queryWrapper) && queryWrapper instanceof AbstractWrapper) {
            ((AbstractWrapper<?, ?, ?>) queryWrapper).last("limit 1");
        }
        List<T> list = selectList(queryWrapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询列表数据，并转换为map
     *
     * @param queryWrapper  查询条件
     * @param keyMapper     key生成器
     * @param valueMapper   value生成器
     * @param <K>           key类型
     * @param <V>           value类型
     * @param mergeFunction key冲突时做merge处理
     * @return map结果
     */
    default <K, V> Map<K, V> selectListToMap(Wrapper<T> queryWrapper, Function<T, K> keyMapper, Function<T, V> valueMapper, BinaryOperator<V> mergeFunction) {
        return selectList(queryWrapper).stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    /**
     * 查询列表数据，并转换为map
     *
     * @param queryWrapper 查询条件
     * @param keyMapper    key生成器
     * @param valueMapper  value生成器
     * @param <K>          key类型
     * @param <V>          value类型
     * @return map结果
     */
    default <K, V> Map<K, V> selectListToMap(Wrapper<T> queryWrapper, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return selectListToMap(queryWrapper, keyMapper, valueMapper, (v, v2) -> v);
    }

    /**
     * 查询列表数据，并转换为map，其中value值为实体本身
     *
     * @param queryWrapper 查询条件
     * @param keyMapper    key生成器
     * @param <K>          key类型
     * @return map结果
     */
    default <K> Map<K, T> selectListToMap(Wrapper<T> queryWrapper, Function<T, K> keyMapper) {
        return selectListToMap(queryWrapper, keyMapper, Function.identity(), (v, v2) -> v);
    }

    /**
     * 分页查询，使用miemiemie自带的Page对象
     *
     * @param page         分页信息
     * @param queryWrapper 查询条件
     * @param <P>          分页对象
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    default <P extends Page<T>> P selectPageAndConvert(P page, Wrapper<T> queryWrapper) {
        if (page == null) {
            return (P) Pages.toPage(selectPage(null, queryWrapper), null);
        } else {
            return (P) Pages.toPage(
                    selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getCurrentPage(), page.getPageSize()), queryWrapper),
                    null
            );
        }
    }

    /**
     * 如果主键值为空，插入；如果主键值不为空，进行更新操作
     *
     * @param entity 目标实体
     */
    default int insertOrUpdate(T entity) {
        if (entity == null) {
            throw new NullPointerException("entity is null");
        }

        Object primaryKey = MybatisUtil.getEntityPrimaryKey(new Holder<>(), entity);
        if (Objects.isNull(primaryKey)) {
            return insert(entity);
        } else {
            return updateById(entity);
        }
    }

    /**
     * 批量插入实体
     *
     * @param entityList 实体列表
     * @return 插入的条数
     */
    int insertBatch(Collection<T> entityList);

    /**
     * 通过id批量更新实体
     *
     * @param entityList 实体列表
     * @return 更新的条数
     */
    int updateBatchById(@Param(Constants.LIST) Collection<T> entityList);

    /**
     * 计算需要批量更新列表数据的每条数据所要的操作
     *
     * @param preList 数据库中的数据
     * @param curList 要更新新的列表数据
     * @return 按照要进行操作进行分类的数据列表
     */
    default OprDataGroupList<T> autoGroupBatch(List<T> preList, List<T> curList) {

        OprDataGroupList<T> res = new OprDataGroupList<>();
        if (CollectionUtils.isEmpty(preList) && CollectionUtils.isEmpty(curList)) {
            return res;
        }

        // 设置默认值，并清空为null的元素
        preList = Optional.ofNullable(preList).orElse(Collections.emptyList()).stream().filter(Objects::nonNull).collect(Collectors.toList());
        curList = Optional.ofNullable(curList).orElse(Collections.emptyList()).stream().filter(Objects::nonNull).collect(Collectors.toList());

        // 判断是否是 Mybatis Plus 实体
        Holder<TableInfo> tableInfoHolder = new Holder<>();

        Map<Object, T> preIdValMap = new HashMap<>(16);

        preList.forEach(entity -> {
            Object primaryKey = MybatisUtil.getEntityPrimaryKey(tableInfoHolder, entity);
            Assert.notNull(primaryKey, "数据库查询结果记录的主键不可为null，发生了不可能存在的情况！");
            preIdValMap.put(primaryKey, entity);
        });

        List<T> finalPreList = new ArrayList<>(preList);
        curList.forEach(entity -> {
            Object primaryKey = MybatisUtil.getEntityPrimaryKey(tableInfoHolder, entity);
            T preEntity = preIdValMap.get(primaryKey);
            if (Objects.isNull(primaryKey)) {
                res.getNeedAdd().add(entity);
                return;
            }
            if (!preIdValMap.containsKey(primaryKey)) {
                res.getDirtyData().add(entity);
                return;
            }
            if (entity.equals(preEntity)) {
                res.getNotChange().add(entity);
            } else {
                res.getNeedUpdate().add(entity);
            }
            finalPreList.remove(preEntity);
        });
        // 剩余的元素实体就是需要删除的
        res.getNeedDelete().addAll(finalPreList);
        return res;
    }

    /**
     * 批量更新数据
     * <p>
     * 更新entity list数据到数据库中，并遵循一下规则：
     * 如果curList的元素的id为空，则视为新增的
     * 如果curList的某些元素是preList没有的，且id不为空的，则视为脏数据，会抛出RuntimeException
     * 如果preList的某些元素是curList没有的，且id不为空的，则视为删除的
     * 如果preList与curList都有的，且id不为空的，如果两人equals不等，则视为更新
     * <p>
     * 注意：调用此方法时一定要加事务
     *
     * @param preList 修改之前的 entity list
     * @param curList 要修改的 entity list
     * @return 分组结果
     */
    default OprDataGroupList<T> autoUpdateBatch(List<T> preList, List<T> curList) {
        OprDataGroupList<T> groupList = autoGroupBatch(preList, curList);
        autoUpdateBatch(groupList);
        return groupList;
    }

    /**
     * 批量更新数据
     * <p>
     * 注意：调用此方法时一定要加事务
     *
     * @param wrapper 要更新的列表数据的查询wrapper
     * @param curList 新的数据
     * @return 分组结果
     */
    default OprDataGroupList<T> autoUpdateBatch(Wrapper<T> wrapper, List<T> curList) {
        return autoUpdateBatch(selectList(wrapper), curList);
    }

    /**
     * 批量更新数据
     * <p>
     * 注意：调用此方法时一定要加事务
     *
     * @param groupList 已经分组的数据
     */
    default void autoUpdateBatch(OprDataGroupList<T> groupList) {
        if (Objects.isNull(groupList)) {
            return;
        }
        insertBatch(groupList.getNeedAdd());
        updateBatchById(groupList.getNeedUpdate());
        deleteBatchIds(groupList.getNeedDelete());
    }

}
