package com.miemiemie.starter.core.page;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * page对象转换，将其他类型的page对象转换为core包的Page对象
 *
 * @author yangshunxiang
 * @since 2023/01/25
 */
@SuppressWarnings("unused")
public interface PageConvert<P> extends InitializingBean {

    <T> Page<T> convert(Object page);

    @Override
    default void afterPropertiesSet() {
        ParameterizedType pt = null;
        Type[] interfaces = this.getClass().getGenericInterfaces();
        for (Type i : interfaces) {
            if (PageConvert.class == ((ParameterizedType) i).getRawType()) {
                pt = (ParameterizedType) (((ParameterizedType) i).getActualTypeArguments())[0];
            }
        }
        Assert.notNull(pt, "PageConvert Type Arguments is null");
        PageConvertFactory.registryConvert((Class<?>) pt.getRawType(), this);
    }
}
