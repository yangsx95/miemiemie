package com.miemiemie.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author yangshunxiang
 */
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext APPLICATION_CONTEXT = null;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static @NonNull ApplicationContext getHolder() {
        assertContextInjected();
        return APPLICATION_CONTEXT;
    }

    public static <T> T getBean(Class<T> beanClass) {
        assertContextInjected();
        return APPLICATION_CONTEXT.getBean(beanClass);
    }

    public static Object getBean(String name) {
        assertContextInjected();
        return APPLICATION_CONTEXT.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> beanClass) {
        assertContextInjected();
        return APPLICATION_CONTEXT.getBean(name, beanClass);
    }

    @Override
    public void destroy() {
        SpringContextHolder.clearHolder();
    }

    private static void clearHolder() {
        APPLICATION_CONTEXT = null;
    }

    private static void assertContextInjected() {
        Assert.notNull(APPLICATION_CONTEXT, "ApplicationContext未注入");
    }
}
