package com.miemiemie.starter.core.lang;

/**
 * 可抛出异常的Runnable接口
 *
 * @author yangshunxiang
 * @since 2023/4/30
 */
@FunctionalInterface
public interface CheckedRunnable {

    void run() throws Exception;

}
