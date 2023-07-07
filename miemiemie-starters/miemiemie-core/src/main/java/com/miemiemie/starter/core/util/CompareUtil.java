package com.miemiemie.starter.core.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对象比较工具
 *
 * @author yangshunxiang
 * @since 2023/3/14
 */
public class CompareUtil {

    @Data
    public static class GroupListData<S, T> {
        /**
         * source多出的数据
         */
        private List<S> sourceExtra = new ArrayList<>();
        /**
         * target多出的数据
         */
        private List<T> targetExtra = new ArrayList<>();
        /**
         * source和target都有，但是target中有变动的数据
         */
        private List<ModifiedData<S, T>> targetChanged = new ArrayList<>();
    }

    @AllArgsConstructor
    @Data
    public static class ModifiedData<S, T> {
        private S source;

        private T target;
    }

    public static <S, T> GroupListData<S, T> compareAndGroup(List<S> sourceList,
                                                             List<T> targetList,
                                                             Function<S, Object> sourceHashGenerator,
                                                             Function<T, Object> targetHashGenerator) {
        return compareAndGroup(sourceList, targetList, sourceHashGenerator, targetHashGenerator, (o1, o2) -> true);
    }

    /**
     * 比较并分组
     *
     * @param sourceList          源List
     * @param targetList          目标List
     * @param sourceHashGenerator 用作hash比较的hash生成器，他会为每个元素生成hash值
     * @param targetHashGenerator 用作hash比较的hash生成器，他会为每个元素生成hash值
     * @param equator             用作对象是否修改比较的对象比较器
     * @param <T>                 元素类型
     * @return 分组信息
     */
    public static <S, T> GroupListData<S, T> compareAndGroup(List<S> sourceList,
                                                             List<T> targetList,
                                                             Function<S, Object> sourceHashGenerator,
                                                             Function<T, Object> targetHashGenerator,
                                                             Equator<S, T> equator) {
        Assert.notNull(sourceHashGenerator, "hash生成器不可为null");
        Assert.notNull(targetHashGenerator, "hash生成器不可为null");
        Assert.notNull(equator, "比较器不可为null");
        GroupListData<S, T> res = new GroupListData<>();
        if (CollectionUtils.isEmpty(sourceList) && CollectionUtils.isEmpty(targetList)) {
            return res;
        }
        // 默认值
        sourceList = Optional.ofNullable(sourceList).orElse(Collections.emptyList());
        targetList = Optional.ofNullable(targetList).orElse(Collections.emptyList());
        // 清空null元素
        sourceList.removeIf(Objects::isNull);
        targetList.removeIf(Objects::isNull);
        // 构建sourceList以及targetList的 HashMap，如果存在Hash相同的数据，将他们放在同一个key中，value List 的元素是多个
        Map<Object, List<S>> sourceMap = sourceList.stream().collect(Collectors.groupingBy(sourceHashGenerator));
        Map<Object, List<T>> targetMap = targetList.stream().collect(Collectors.groupingBy(targetHashGenerator));
        sourceMap.forEach((sk, svList) -> {
            List<T> tvList = targetMap.getOrDefault(sk, Collections.emptyList());
            int sourceExtraCount = Math.max(svList.size() - tvList.size(), 0);
            int targetExtraCount = Math.max(tvList.size() - svList.size(), 0);

            // 设置源list多的数据
            res.getSourceExtra().addAll(svList.subList(svList.size() - sourceExtraCount, svList.size()));
            // 设置目标list多的数据，
            res.getTargetExtra().addAll(tvList.subList(tvList.size() - targetExtraCount, tvList.size()));
            // 源list和目标list共有的几条数据，比较他们是否发生变化
            for (int i = 0; i < Math.min(svList.size(), tvList.size()); i++) {
                boolean isEqual = equator.equals(svList.get(i), tvList.get(i));
                if (!isEqual) {
                    res.getTargetChanged().add(new ModifiedData<>(svList.get(i), tvList.remove(i)));
                }
            }
            targetMap.remove(sk);
        });
        targetMap.forEach((tk, tv) -> res.getTargetExtra().addAll(tv));
        return res;
    }

}