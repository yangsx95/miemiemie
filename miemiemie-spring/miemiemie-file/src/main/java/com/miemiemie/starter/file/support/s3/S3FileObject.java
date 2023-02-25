package com.miemiemie.starter.file.support.s3;

import com.amazonaws.services.s3.model.S3Object;
import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;

import java.io.InputStream;
import java.util.function.Supplier;

/**
 * S3协议的文件对象
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public class S3FileObject implements FileObject {

    private final S3Object s3Object;

    public S3FileObject(S3Object s3Object) {
        this.s3Object = s3Object;
    }

    @Override
    public String getPart() {
        return s3Object.getBucketName();
    }

    @Override
    public String getFilePath() {
        return s3Object.getKey();
    }

    @Override
    public FileMetadata getMetaData() {
        return FileMetadata.builder()
                .contentType(s3Object.getObjectMetadata().getContentType())
                .fileSizeByte(s3Object.getObjectMetadata().getContentLength())
                .putAll(s3Object.getObjectMetadata().getUserMetadata())
                .build();
    }

    @Override
    public Supplier<InputStream> getContent() {
        return s3Object::getObjectContent;
    }
}
