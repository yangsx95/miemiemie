package com.miemiemie.starter.file.support.ftp;

import com.miemiemie.core.util.MimeTypes;
import com.miemiemie.starter.file.FileMetadata;
import org.springframework.util.Assert;

/**
 * ftp文件元数据
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
public class FtpFileMetadataBuilder {

    private final FileMetadata fileMetaData = new FileMetadata();

    public FtpFileMetadataBuilder fileName(String fileName) {
        Assert.hasText(fileName, "文件名称不能为空");
        this.fileMetaData.setFileName(fileName);
        return this;
    }

    public FtpFileMetadataBuilder fileExtension(String fileExtension) {
        Assert.hasText(fileExtension, "文件扩展名不可为空");
        this.fileMetaData.setFileExtension(fileExtension);
        this.fileMetaData.setContentType(MimeTypes.getMimeType(this.fileMetaData.getFileExtension()));
        return this;
    }

    public FtpFileMetadataBuilder size(long size) {
        Assert.isTrue(size >= 0, "文件大小不能小于0");
        this.fileMetaData.setSize(size);
        return this;
    }

    public FileMetadata build() {
        return fileMetaData;
    }

}
