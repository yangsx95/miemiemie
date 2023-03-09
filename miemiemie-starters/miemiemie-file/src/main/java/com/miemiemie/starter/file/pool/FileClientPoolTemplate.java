package com.miemiemie.starter.file.pool;

import com.miemiemie.starter.file.exception.FileClientException;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Objects;

/**
 * @author yangshunxiang
 * @since 2023/2/25
 */
public class FileClientPoolTemplate<T> {

    private final GenericObjectPool<T> pool;

    public FileClientPoolTemplate(PooledObjectFactory<T> factory, GenericObjectPoolConfig<T> poolConfig) {
        this.pool = new GenericObjectPool<>(factory, poolConfig);
    }

    @FunctionalInterface
    public interface OprSupplier<C, R> {

        R opr(C c) throws Exception;

    }

    public  <R> R getClientAndOpr(OprSupplier<T, R> doSomethingSupplier, String errorMsg) {
        T client = null;
        try {
            client = pool.borrowObject();
            return doSomethingSupplier.opr(client);
        } catch (Exception e) {
            throw new FileClientException(errorMsg, e);
        } finally {
            if (Objects.nonNull(client)) {
                pool.returnObject(client);
            }
        }
    }
}
