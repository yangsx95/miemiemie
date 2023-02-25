package com.miemiemie.starter.file.support.local;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.exception.FileClientException;
import com.miemiemie.starter.file.support.AbstractFileClient;
import com.miemiemie.starter.file.support.FilePathGenerator;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * 本地文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public class LocalFileClient extends AbstractFileClient {

    @Getter
    private LocalFileClientProperties localFileClientProperties = LocalFileClientProperties.DEFAULT;

    public LocalFileClient(FilePathGenerator filePathGenerator, FileMetadata defaultMetaData) {
        super(filePathGenerator, defaultMetaData);
    }

    public LocalFileClient(LocalFileClientProperties localFileClientProperties, FilePathGenerator filePathGenerator, FileMetadata defaultMetaData) {
        super(filePathGenerator, defaultMetaData);
        this.localFileClientProperties = localFileClientProperties;
    }

    public static void createFile(File file) throws IOException {
        if (file.exists()) {
            return;
        }

        // 如果目录不存在，创建目录
        if (!file.getParentFile().exists()) {
            boolean ignore = file.getParentFile().mkdirs();
        }
        boolean ignore = file.createNewFile();
    }


    @Override
    public FileObject putFile(String part, InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        File file = Paths.get(part, filepath).toFile();
        if (!file.exists()) {
            try {
                createFile(file);
            } catch (IOException e) {
                throw new FileClientException("file put failed, because file create", e);
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] b = new byte[1024];
            while ((content.read(b)) != -1) {
                fos.write(b);// 写入数据
            }
        } catch (IOException e) {
            throw new FileClientException("file put failed", e);
        }
        return new LocalFileObject(part, filepath);
    }

    @Override
    public Optional<FileObject> getFile(String baseDir, String filepath) {
        return Optional.of(new LocalFileObject(baseDir, filepath));
    }

    @Override
    public boolean exists(String part, String filepath) {
        return Paths.get(part, filepath).toFile().exists();
    }

    @Override
    public URI getUrl(String baseDir, String filepath) {
        return Paths.get(baseDir, filepath).toFile().toURI();
    }

    @Override
    public void deleteFile(String part, String filepath) throws FileClientException {
        boolean ignore = Paths.get(part, filepath).toFile().delete();
    }

    @Override
    protected String getDefaultPart() {
        return localFileClientProperties.getBaseDir();
    }
}
