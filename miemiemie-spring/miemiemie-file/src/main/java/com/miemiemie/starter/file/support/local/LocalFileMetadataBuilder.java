package com.miemiemie.starter.file.support.local;

import com.miemiemie.core.util.MimeTypes;
import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.util.Util;
import org.springframework.util.Assert;

import java.io.File;

/**
 * 本地文件元信息建造器
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public class LocalFileMetadataBuilder {

    private final FileMetadata fileMetaData = new FileMetadata();

    public LocalFileMetadataBuilder fileName(String fileName) {
        Assert.hasText(fileName, "文件名称不能为空");
        this.fileMetaData.setFileName(fileName);
        return this;
    }

    public LocalFileMetadataBuilder fileExtension(String fileExtension) {
        Assert.hasText(fileExtension, "文件扩展名不可为空");
        this.fileMetaData.setFileExtension(fileExtension);
        this.fileMetaData.setContentType(MimeTypes.getMimeType(this.fileMetaData.getFileExtension()));
        return this;
    }

    public LocalFileMetadataBuilder size(long size) {
        Assert.isTrue(size >= 0, "文件大小不能小于0");
        this.fileMetaData.setSize(size);
        return this;
    }

    public LocalFileMetadataBuilder file(File file) {
        Assert.notNull(file, "文件对象不可为空");
        this.fileMetaData.setFileName(file.getName());
        this.fileMetaData.setFileExtension(Util.getFileExtension(file.getName()));
        this.fileMetaData.setContentType(MimeTypes.getMimeType(this.fileMetaData.getFileExtension()));
        this.fileMetaData.setSize(file.length());
        return this;
    }

    public FileMetadata build() {
        return fileMetaData;
    }

}
