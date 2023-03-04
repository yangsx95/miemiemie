package com.miemiemie.file.support.local;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.exception.FileClientException;
import com.miemiemie.file.support.AbstractFileClient;
import com.miemiemie.file.FilePathGenerator;
import lombok.Getter;

import java.io.*;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 本地文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public class LocalFileClient extends AbstractFileClient {

    @Getter
    private LocalFileClientProperties localFileClientProperties = LocalFileClientProperties.DEFAULT;

    public LocalFileClient(FilePathGenerator filePathGenerator) {
        super(filePathGenerator);
    }

    public LocalFileClient(LocalFileClientProperties localFileClientProperties, FilePathGenerator filePathGenerator) {
        super(filePathGenerator);
        this.localFileClientProperties = localFileClientProperties;
    }

    private static void createFile(File file) throws IOException {
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
            byte[] buffer = new byte[1024];
            int index;
            while ((index = content.read(buffer)) != -1) {
                fos.write(buffer, 0, index);
                fos.flush();
            }
        } catch (IOException e) {
            throw new FileClientException("file put failed", e);
        } finally {
            try {
                content.close();
            } catch (IOException ignore) {
            }
        }
        return generateFileObjectFromFile(part, filepath, file);
    }

    @Override
    public Optional<FileObject> getFile(String baseDir, String filepath) {
        File file = Paths.get(baseDir, filepath).toFile();
        if (!file.exists()) {
            return Optional.empty();
        }
        return Optional.of(generateFileObjectFromFile(baseDir, filepath, file));
    }

    private static FileObject generateFileObjectFromFile(String baseDir, String filepath, File file) {
        FileMetadata fileMetadata = FileMetadata.builder().ofFile(file).build();
        return FileObject.builder()
                .part(baseDir)
                .filepath(filepath)
                .contentSupplier(getFileInputStream(file))
                .metadata(fileMetadata)
                .build();
    }

    private static Supplier<InputStream> getFileInputStream(File file) {
        return () -> {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new FileClientException("get file content error", e);
            }
        };
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
