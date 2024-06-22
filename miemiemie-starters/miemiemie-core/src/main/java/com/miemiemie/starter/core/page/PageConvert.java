package com.miemiemie.starter.core.page;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

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

        // 将他的子类，实现类也注册到这个转换器上
        // 本来打算做扫描子类与实现类的，但是考虑到性能，所以采取了手动指定的方式
        registrySubClass().forEach(clazz -> PageConvertFactory.registryConvert(clazz, this));
    }

    default Set<Class<?>> registrySubClass() {
        return Collections.emptySet();
    }

}
