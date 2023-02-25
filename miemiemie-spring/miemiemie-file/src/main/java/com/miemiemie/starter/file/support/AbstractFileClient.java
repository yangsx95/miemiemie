package com.miemiemie.starter.file.support;

import com.miemiemie.starter.file.FileClient;
import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.exception.FileClientException;
import lombok.Getter;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

/**
 * 文件客户端抽象
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public abstract class AbstractFileClient implements FileClient {

    @Getter
    private final FilePathGenerator filePathGenerator;

    @Getter
    private final FileMetadata defaultMetaData;

    public AbstractFileClient(FilePathGenerator filePathGenerator, FileMetadata defaultMetaData) {
        this.filePathGenerator = filePathGenerator;
        this.defaultMetaData = defaultMetaData;
    }

    @Override
    public FileObject putFile(String part, InputStream content, FileMetadata fileMetaData) throws FileClientException {
        return putFile(part, content, filePathGenerator.generate(fileMetaData), fileMetaData);
    }

    @Override
    public FileObject putFile(String part, InputStream content, String filepath) throws FileClientException {
        return putFile(part, content, filepath, defaultMetaData);
    }

    @Override
    public FileObject putFile(InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        return putFile(getDefaultPart(), content, filepath, defaultMetaData);
    }

    @Override
    public FileObject putFile(InputStream content, FileMetadata fileMetaData) throws FileClientException {
        return putFile(getDefaultPart(), content, filePathGenerator.generate(fileMetaData), defaultMetaData);
    }

    @Override
    public FileObject putFile(InputStream content, String filepath) throws FileClientException {
        return putFile(getDefaultPart(), content, filepath, defaultMetaData);
    }

    @Override
    public FileObject putFile(InputStream content) throws FileClientException {
        return putFile(getDefaultPart(), content, filePathGenerator.generate(defaultMetaData), defaultMetaData);
    }

    @Override
    public Optional<FileObject> getFile(String filepath) {
        return getFile(getDefaultPart(), filepath);
    }

    @Override
    public boolean exists(String filepath) {
        return exists(getDefaultPart(), filepath);
    }

    @Override
    public URI getUrl(String filepath) {
        return getUrl(getDefaultPart(), filepath);
    }

    @Override
    public void deleteFile(String filepath) throws FileClientException {
        deleteFile(getDefaultPart(), filepath);
    }

    protected abstract String getDefaultPart();


}
