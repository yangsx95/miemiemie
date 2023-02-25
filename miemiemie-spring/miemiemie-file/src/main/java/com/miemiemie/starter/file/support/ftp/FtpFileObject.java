package com.miemiemie.starter.file.support.ftp;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.util.Util;
import lombok.Getter;

import java.io.InputStream;
import java.util.function.Supplier;

/**
 * ftp文件对象
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
public class FtpFileObject implements FileObject {

    @Getter
    private final String baseDir;

    @Getter
    private final String filePath;

    private final Supplier<InputStream> content;

    protected FtpFileObject(String baseDir, String filePath, Supplier<InputStream> content) {
        this.baseDir = baseDir;
        this.filePath = filePath;
        this.content = content;
    }

    @Override
    public String getPart() {
        return getBaseDir();
    }

    @Override
    public FileMetadata getMetaData() {
        return FileMetadata.builder()
                .ofFilename(Util.getFilename(getPart(), getFilePath()))
                .build();
    }

    @Override
    public Supplier<InputStream> getContent() {
        return content;
    }

}
