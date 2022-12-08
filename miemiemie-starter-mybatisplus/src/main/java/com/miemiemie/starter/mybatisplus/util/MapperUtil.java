package com.miemiemie.starter.mybatisplus.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.*;

/**
 * 自定义扩展Mapper工具（基于Mybatis Plus）
 *
 * @author 杨顺翔
 * @since 2022/07/10
 */
public class MapperUtil {

    private MapperUtil() throws IllegalAccessException {
        throw new IllegalAccessException("can not instance!");
    }

    /**
     * 批量更新列表数据
     * <p>
     * 更新entity list数据到数据库中，并遵循一下规则：
     * 如果curList的元素的id为空，则视为新增的
     * 如果curList的某些元素是preList没有的，且id不为空的，则视为脏数据，会抛出RuntimeException
     * 如果preList的某些元素是curList没有的，且id不为空的，则视为删除的
     * 如果preList与curList都有的，且id不为空的，如果两人equals不等，则视为更新
     * <p>
     * 注意：调用此方法时一定要加事务
     *
     * @param mapper      数据库Mapper
     * @param preList     修改之前的 entity list
     * @param curList     要修改的 entity list
     * @param entityClass 实体类class
     * @param <T>         entity 类型
     */
    public static <T> void updateList(BaseMapper<T> mapper, List<T> preList, List<T> curList, Class<T> entityClass) {
        if (mapper == null) {
            throw new NullPointerException("mapper处理类不能为空");
        }

        if (entityClass == null) {
            throw new NullPointerException("entityClass实体类类型不能为空");
        }

        OprDataGroupList<T> list = groupData(preList, curList, entityClass);

        if (!CollectionUtils.isEmpty(list.getDirtyData())) {
            throw new RuntimeException("更新的数据不正确");
        }

        updateList(mapper, list);
    }

    /**
     * 更新list
     *
     * @param mapper    Mapper
     * @param groupList 分组数据信息
     * @param <T>       实体类型
     */
    public static <T> void updateList(BaseMapper<T> mapper, OprDataGroupList<T> groupList) {
        if (Objects.isNull(groupList)) {
            return;
        }
        groupList.getNeedAdd().forEach(mapper::insert);
        groupList.getNeedUpdate().forEach(mapper::updateById);
        mapper.deleteBatchIds(groupList.getNeedDelete());
    }

    public static class OprDataGroupList<T> {
        private final List<T> needDelete = new ArrayList<>();
        private final List<T> needUpdate = new ArrayList<>();
        private final List<T> needAdd = new ArrayList<>();
        private final List<T> dirtyData = new ArrayList<>();

        private final List<T> notChange = new ArrayList<>();

        public List<T> getNeedDelete() {
            return needDelete;
        }

        public List<T> getNeedUpdate() {
            return needUpdate;
        }

        public List<T> getNeedAdd() {
            return needAdd;
        }

        public List<T> getDirtyData() {
            return dirtyData;
        }

        public List<T> getNotChange() {
            return notChange;
        }
    }

    /**
     * 计算需要批量更新列表数据的每条数据所要的操作
     *
     * @param preList     数据库中的数据
     * @param curList     要更新新的列表数据
     * @param entityClass 实体类Class
     * @param <T>         实体类型
     * @return 按照要进行操作进行分类的数据列表
     */
    public static <T> OprDataGroupList<T> groupData(List<T> preList, List<T> curList, Class<T> entityClass) {
        Objects.requireNonNull(entityClass, "entityClass不可以为null");

        OprDataGroupList<T> res = new OprDataGroupList<>();
        if (CollectionUtils.isEmpty(preList) && CollectionUtils.isEmpty(curList)) {
            return res;
        }

        // 默认值
        preList = Optional.ofNullable(preList).orElse(Collections.emptyList());
        curList = Optional.ofNullable(curList).orElse(Collections.emptyList());

        // 清空null元素
        preList.removeIf(Objects::isNull);
        curList.removeIf(Objects::isNull);

        // 判断是否是 Mybatis Plus 实体
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");

        // 获取主键属性名称
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");

        Map<Object, T> preIdValMap = new HashMap<>(16);

        preList.forEach(entity -> {
            Object primaryKey = tableInfo.getPropertyValue(entity, keyProperty);
            Assert.notNull(primaryKey, "数据库查询结果记录的主键不可为null，发生了不可能存在的情况！");
            preIdValMap.put(primaryKey, entity);
        });

        List<T> finalPreList = new ArrayList<>(preList);
        curList.forEach(entity -> {
            Object primaryKey = tableInfo.getPropertyValue(entity, keyProperty);
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
}
