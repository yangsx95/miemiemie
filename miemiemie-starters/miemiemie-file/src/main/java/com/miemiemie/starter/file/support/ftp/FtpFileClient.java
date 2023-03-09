package com.miemiemie.starter.file.support.ftp;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.FilePathGenerator;
import com.miemiemie.starter.file.exception.FileClientException;
import com.miemiemie.starter.file.pool.AbstractPooledFileClient;
import com.miemiemie.starter.file.util.Util;
import lombok.Getter;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObjectFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/**
 * ftp文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
public class FtpFileClient extends AbstractPooledFileClient<FTPClient> {

    public static final String ROOT_PATH = "/";
    @Getter
    private final FtpFileClientProperties properties;

    public FtpFileClient(FtpFileClientProperties properties,
                         FilePathGenerator filePathGenerator) {
        this(properties, filePathGenerator, new FtpClientFactory(properties));
    }

    public FtpFileClient(FtpFileClientProperties properties,
                         FilePathGenerator filePathGenerator,
                         PooledObjectFactory<FTPClient> factory) {
        super(filePathGenerator, properties.getPool(), factory);
        this.properties = properties;
    }

    /**
     * 创建多级目录
     *
     * @param ftp  一个连接有效的ftp连接
     * @param path 可能需要创建的多级目录路径，/分隔
     */
    public static void createDirs(FTPClient ftp, String path) throws Exception {
        /*该部分为逐级创建*/
        String[] split = path.split("/");

        for (String str : split) {
            if (!StringUtils.hasText(str)) {
                continue;
            }
            if (!ftp.changeWorkingDirectory(str)) {
                ftp.makeDirectory(str);
                ftp.changeWorkingDirectory(str);
            }
        }
    }


    @Override
    protected FileObject doPutFile(FTPClient client, String part, InputStream content, String filepath, FileMetadata fileMetaData) throws Exception {
        String dir = Util.getFileDirPath(part, filepath);
        createDirs(client, dir);
        client.changeWorkingDirectory(dir);
        client.storeFile(Util.getFilename(part, filepath), content);

        if (!doExists(client, part, filepath)) {
            throw new FileClientException("file upload failed, uploaded file not exists");
        }

        FileMetadata metadata = FileMetadata.builder()
                .ofFilepath(filepath)
                .build();

        return FileObject.builder()
                .part(part)
                .filepath(filepath)
                .metadata(metadata)
                .contentSupplier(() -> getPoolTemplate()
                        .getClientAndOpr((c) -> getFileInputStream(c, part, filepath), "file get error"))
                .build();
    }

    @Override
    protected Optional<FileObject> doGetFile(FTPClient client, String part, String filepath) throws Exception {
        if (!doExists(client, part, filepath)) {
            return Optional.empty();
        }

        FileMetadata metadata = FileMetadata.builder()
                .ofFilepath(filepath)
                .build();
        return Optional.of(FileObject.builder()
                .part(part)
                .filepath(filepath)
                .metadata(metadata)
                .contentSupplier(() -> getFileInputStream(client, part, filepath))
                .build());
    }


    private InputStream getFileInputStream(FTPClient client, String part, String filepath) {
        try {
            client.changeWorkingDirectory(Util.getFileDirPath(part, filepath));
            return client.retrieveFileStream(Util.getFilename(part, filepath));
        } catch (IOException e) {
            throw new FileClientException("get file content error", e);
        }
    }

    @Override
    protected boolean doExists(FTPClient client, String part, String filepath) throws Exception {
        client.changeWorkingDirectory(ROOT_PATH);
        String[] names = client.listNames(Util.getAbsFilepath(part, filepath));
        return Objects.nonNull(names) && names.length > 0;
    }

    @Override
    protected URI doGetUrl(FTPClient client, String part, String filepath) throws Exception {
        return new URI("ftp",
                properties.getUsername() + ":" + properties.getPassword(),
                properties.getHost(), properties.getPort(),
                Util.getAbsFilepath(part, filepath),
                null, null);
    }

    @Override
    protected Void doDelete(FTPClient client, String part, String filepath) throws Exception {
        client.changeWorkingDirectory(ROOT_PATH);
        client.deleteFile(Util.getAbsFilepath(part, filepath));
        return null;
    }

    @Override
    protected String getDefaultPart() {
        return properties.getBaseDir();
    }

}
