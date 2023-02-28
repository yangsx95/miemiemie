package com.miemiemie.file.pool;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.exception.FileClientException;
import com.miemiemie.file.support.AbstractFileClient;
import com.miemiemie.file.FilePathGenerator;
import lombok.Getter;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

/**
 * 支持线程池的抽象文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
public abstract class AbstractPooledFileClient<T> extends AbstractFileClient {

    @Getter
    private final FileClientPoolTemplate<T> poolTemplate;

    public AbstractPooledFileClient(FilePathGenerator filePathGenerator,
                                    FileClientPoolProperties poolProperties,
                                    PooledObjectFactory<T> factory) {
        super(filePathGenerator);
        poolTemplate = new FileClientPoolTemplate<>(factory, getPoolConfig(poolProperties));
    }

    protected  GenericObjectPoolConfig<T> getPoolConfig(FileClientPoolProperties poolProperties) {
        GenericObjectPoolConfig<T> config = new GenericObjectPoolConfig<>();
        config.setMaxIdle(poolProperties.getMaxIdle());
        config.setMaxTotal(poolProperties.getMaxTotal());
        config.setMinIdle(poolProperties.getMinIdle());
        return config;
    }

    @Override
    public FileObject putFile(String part, InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        return poolTemplate.getClientAndOpr(client -> doPutFile(client, part, content, filepath, fileMetaData), "put file error");
    }

    protected abstract FileObject doPutFile(T client, String part, InputStream content, String filepath, FileMetadata fileMetaData) throws Exception;

    @Override
    public Optional<FileObject> getFile(String part, String filepath) {
        return poolTemplate.getClientAndOpr(client -> doGetFile(client, part, filepath), "get file error");
    }

    protected abstract Optional<FileObject> doGetFile(T client, String part, String filepath) throws Exception;

    @Override
    public boolean exists(String part, String filepath) {
        return poolTemplate.getClientAndOpr(client -> doExists(client, part, filepath), "file exists error");
    }

    protected abstract boolean doExists(T client, String part, String filepath) throws Exception;


    @Override
    public URI getUrl(String part, String filepath) {
        return poolTemplate.getClientAndOpr(client -> doGetUrl(client, part, filepath), "file exists error");
    }

    protected abstract URI doGetUrl(T client, String part, String filepath) throws Exception;

    @Override
    public void deleteFile(String part, String filepath) throws FileClientException {
        poolTemplate.getClientAndOpr(client -> doDelete(client, part, filepath), "file exists error");
    }

    protected abstract Void doDelete(T client, String part, String filepath) throws Exception;

}
