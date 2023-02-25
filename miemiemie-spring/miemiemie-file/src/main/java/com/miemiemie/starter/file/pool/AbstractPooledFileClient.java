package com.miemiemie.starter.file.pool;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.exception.FileClientException;
import com.miemiemie.starter.file.support.AbstractFileClient;
import com.miemiemie.starter.file.support.FilePathGenerator;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.InputStream;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/**
 * 支持线程池的抽象文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
public abstract class AbstractPooledFileClient<T> extends AbstractFileClient {

    private final GenericObjectPool<T> pool;

    public AbstractPooledFileClient(FilePathGenerator filePathGenerator,
                                    FileMetadata defaultMetaData,
                                    PooledObjectFactory<T> factory) {
        super(filePathGenerator, defaultMetaData);
        pool = new GenericObjectPool<>(factory, getPoolConfig());
    }

    protected abstract GenericObjectPoolConfig<T> getPoolConfig();

    @FunctionalInterface
    protected interface OprSupplier<C, R> {

        R opr(C c) throws Exception;

    }

    protected <R> R template(OprSupplier<T, R> doSomethingSupplier, String errorMsg) {
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

    @Override
    public FileObject putFile(String part, InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        return template(client -> doPutFile(client, part, content, filepath, fileMetaData), "put file error");
    }

    protected abstract FileObject doPutFile(T client, String part, InputStream content, String filepath, FileMetadata fileMetaData) throws Exception;

    @Override
    public Optional<FileObject> getFile(String part, String filepath) {
        return template(client -> doGetFile(client, part, filepath), "get file error");
    }

    protected abstract Optional<FileObject> doGetFile(T client, String part, String filepath) throws Exception;

    @Override
    public boolean exists(String part, String filepath) {
        return template(client -> doExists(client, part, filepath), "file exists error");
    }

    protected abstract boolean doExists(T client, String part, String filepath) throws Exception;


    @Override
    public URI getUrl(String part, String filepath) {
        return template(client -> doGetUrl(client, part, filepath), "file exists error");
    }

    protected abstract URI doGetUrl(T client, String part, String filepath) throws Exception;

    @Override
    public void deleteFile(String part, String filepath) throws FileClientException {
        template(client -> doDelete(client, part, filepath), "file exists error");
    }

    protected abstract Void doDelete(T client, String part, String filepath) throws Exception;

}
