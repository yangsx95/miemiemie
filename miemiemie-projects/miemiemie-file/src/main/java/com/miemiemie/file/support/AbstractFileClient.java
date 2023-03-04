package com.miemiemie.file.support;

import com.miemiemie.file.FileClient;
import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.FilePathGenerator;
import com.miemiemie.file.exception.FileClientException;
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

    /**
     * 当文件路径不存在时，将会使用该文件路径生成器生成一个文件路径
     */
    @Getter
    private final FilePathGenerator filePathGenerator;

    public AbstractFileClient(FilePathGenerator filePathGenerator) {
        this.filePathGenerator = filePathGenerator;
    }

    @Override
    public FileObject putFile(String part, InputStream content, FileMetadata fileMetaData) throws FileClientException {
        return putFile(part, content, filePathGenerator.generate(fileMetaData), fileMetaData);
    }

    @Override
    public FileObject putFile(String part, InputStream content, String filepath) throws FileClientException {
        return putFile(part, content, filepath, FileMetadata.EMPTY);
    }

    @Override
    public FileObject putFile(InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        return putFile(getDefaultPart(), content, filepath, fileMetaData);
    }

    @Override
    public FileObject putFile(InputStream content, FileMetadata fileMetaData) throws FileClientException {
        return putFile(getDefaultPart(), content, filePathGenerator.generate(fileMetaData), fileMetaData);
    }

    @Override
    public FileObject putFile(InputStream content, String filepath) throws FileClientException {
        return putFile(getDefaultPart(), content, filepath, FileMetadata.EMPTY);
    }

    @Override
    public FileObject putFile(InputStream content) throws FileClientException {
        return putFile(getDefaultPart(), content, filePathGenerator.generate(FileMetadata.EMPTY), FileMetadata.EMPTY);
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
