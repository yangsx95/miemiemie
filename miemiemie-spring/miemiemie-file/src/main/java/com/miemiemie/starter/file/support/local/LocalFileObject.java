package com.miemiemie.starter.file.support.local;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.exception.FileClientException;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.function.Supplier;

/**
 * 本地文件对象
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
@Getter
public class LocalFileObject implements FileObject {

    @Getter
    private final String baseDir;

    @Getter
    private final String filePath;

    @Getter
    private final FileMetadata fileMetadata;

    protected LocalFileObject(String baseDir, String filePath, FileMetadata fileMetadata) {
        this.baseDir = baseDir;
        this.filePath = filePath;
        this.fileMetadata = fileMetadata;
    }

    @Override
    public String getPart() {
        return getBaseDir();
    }

    @Override
    public FileMetadata getMetaData() {
        return FileMetadata.builder().ofFile(getFile()).build();
    }

    @Override
    public Supplier<InputStream> getContent() {
        return () -> {
            try {
                return new FileInputStream(getFile());
            } catch (FileNotFoundException e) {
                throw new FileClientException("get file InputStream failed! ", e);
            }
        };
    }

    public File getFile() {
        return Paths.get(getBaseDir(), getFilePath()).toFile();
    }
}
