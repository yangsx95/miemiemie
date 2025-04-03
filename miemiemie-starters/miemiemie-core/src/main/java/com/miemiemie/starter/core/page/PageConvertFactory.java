package com.miemiemie.starter.core.page;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 分页对象工厂
 *
 * @author yangshunxiang
 * @since 2023/01/25
 */
public class PageConvertFactory {

    private static final ConcurrentMap<Class<?>, PageConvert<?>> PAGE_CONVERTER_MAP = new ConcurrentHashMap<>();

    /**
     * 根据分页
     *
     * @param pageClass 根据分页对象类型获取分页转换bean
     * @return 分页转换bean
     */
    public PageConvert<?> getConvert(Class<?> pageClass) {
        return PAGE_CONVERTER_MAP.get(pageClass);
    }

    /**
     * 注册一个分页转换到工厂中
     *
     * @param pageClass   分页class
     * @param pageConvert 分页转换bean
     */
    public static void registryConvert(Class<?> pageClass, PageConvert<?> pageConvert) {
        PAGE_CONVERTER_MAP.put(pageClass, pageConvert);
    }

}
