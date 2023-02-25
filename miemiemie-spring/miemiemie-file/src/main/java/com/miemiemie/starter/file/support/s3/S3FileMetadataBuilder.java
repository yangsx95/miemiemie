package com.miemiemie.starter.file.support.s3;

import com.amazonaws.services.s3.model.S3Object;
import com.miemiemie.core.util.MimeTypes;
import com.miemiemie.starter.file.FileMetadata;
import org.springframework.util.Assert;

/**
 * S3文件元数据
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public class S3FileMetadataBuilder {

    private final FileMetadata fileMetaData = new FileMetadata();

    public S3FileMetadataBuilder fileName(String fileName) {
        Assert.hasText(fileName, "文件名称不能为空");
        this.fileMetaData.setFileName(fileName);
        return this;
    }

    public S3FileMetadataBuilder fileExtension(String fileExtension) {
        Assert.hasText(fileExtension, "文件扩展名不可为空");
        this.fileMetaData.setFileExtension(fileExtension);
        this.fileMetaData.setContentType(MimeTypes.getMimeType(this.fileMetaData.getFileExtension()));
        return this;
    }

    public S3FileMetadataBuilder contentType(String contentType) {
        Assert.hasText(contentType, "content-type 不可为空");
        this.fileMetaData.setContentType(contentType);
        return this;
    }

    public S3FileMetadataBuilder size(long size) {
        Assert.isTrue(size >= 0, "文件大小不能小于0");
        this.fileMetaData.setSize(size);
        return this;
    }

    public S3FileMetadataBuilder s3Object(S3Object s3Object) {
        Assert.notNull(s3Object, "s3Object cannot null");
        this.fileMetaData.setFileName(s3Object.getObjectMetadata().getUserMetaDataOf(FileMetadata.Fields.fileName));
        this.fileMetaData.setFileExtension(s3Object.getObjectMetadata().getUserMetaDataOf(FileMetadata.Fields.fileExtension));
        this.fileMetaData.setContentType(s3Object.getObjectMetadata().getContentType());
        this.fileMetaData.setSize(s3Object.getObjectMetadata().getContentLength());
        return this;
    }

    public FileMetadata build() {
        return fileMetaData;
    }

}
