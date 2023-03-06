package com.miemiemie.file.ext;

import com.miemiemie.file.FileClient;
import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.exception.FileClientException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 支持秒传策略的文件客户端
 *
 * @author yangshunxiang
 * @since 2023/3/5
 */
public class InstantUploadFileClient implements FileClient {

    @Getter
    private final FileClient originFileClient;

    @Getter
    private final InstantUploadStrategy instantUploadStrategy;

    public InstantUploadFileClient(FileClient originFileClient, InstantUploadStrategy instantUploadStrategy) {
        this.originFileClient = originFileClient;
        this.instantUploadStrategy = instantUploadStrategy;
    }

    private FileObject putFileTemplate(FileMetadata fileMetadata, Supplier<FileObject> fileUploadFunction) {
        Optional<FileObject> fileObjectOptional = instantUploadStrategy.existAndGet(fileMetadata.get(FileMetadata.MD5));
        if (fileObjectOptional.isPresent()) {
            return fileObjectOptional.get();
        }
        FileObject fileObject = fileUploadFunction.get();
        instantUploadStrategy.record(fileObject.getFileMetadata().get(FileMetadata.MD5), fileObject);
        return fileObject;
    }

    @Override
    public FileObject putFile(String part, InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        return putFileTemplate(fileMetaData, () -> originFileClient.putFile(part, content, filepath, fileMetaData));
    }

    @Override
    public FileObject putFile(String part, InputStream content, FileMetadata fileMetaData) throws FileClientException {
        return putFileTemplate(fileMetaData, () -> originFileClient.putFile(part, content, fileMetaData));

    }

    @Override
    public FileObject putFile(String part, InputStream content, String filepath) throws FileClientException {
        return originFileClient.putFile(part, content, filepath);
    }

    @Override
    public FileObject putFile(InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        return putFileTemplate(fileMetaData, () -> originFileClient.putFile(content, filepath, fileMetaData));
    }

    @Override
    public FileObject putFile(InputStream content, FileMetadata fileMetaData) throws FileClientException {
        return putFileTemplate(fileMetaData, () -> originFileClient.putFile(content, fileMetaData));
    }

    @Override
    public FileObject putFile(InputStream content, String filepath) throws FileClientException {
        return originFileClient.putFile(content, filepath);
    }

    @Override
    public FileObject putFile(InputStream content) throws FileClientException {
        return originFileClient.putFile(content);
    }

    @Override
    public Optional<FileObject> getFile(String filepath) {
        return originFileClient.getFile(filepath);
    }

    @Override
    public Optional<FileObject> getFile(String part, String filepath) {
        return originFileClient.getFile(part, filepath);
    }

    @Override
    public boolean exists(String part, String filepath) {
        return originFileClient.exists(part, filepath);
    }

    @Override
    public boolean exists(String filepath) {
        return originFileClient.exists(filepath);
    }

    @Override
    public URI getUrl(String filepath) {
        return originFileClient.getUrl(filepath);
    }

    @Override
    public URI getUrl(String part, String filepath) {
        return originFileClient.getUrl(part, filepath);
    }

    @Override
    public void deleteFile(String part, String filepath) throws FileClientException {
        Optional<FileObject> file = getFile(part, filepath);
        String md5 = file.map(FileObject::getFileMetadata).map(e -> e.get(FileMetadata.MD5)).orElse(null);
        if (file.isPresent()) {
            originFileClient.deleteFile(part, filepath);
        }
        if (StringUtils.hasText(md5)) {
            instantUploadStrategy.deleteRecord(md5);
        }
    }

    @Override
    public void deleteFile(String filepath) throws FileClientException {
        originFileClient.deleteFile(filepath);
    }
}
