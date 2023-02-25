package com.miemiemie.starter.file.support.ftp;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.exception.FileClientException;
import com.miemiemie.starter.file.pool.AbstractPooledFileClient;
import com.miemiemie.starter.file.support.FilePathGenerator;
import com.miemiemie.starter.file.util.Util;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

import static com.miemiemie.starter.file.util.Util.*;

/**
 * ftp文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
@Slf4j
public class FtpFileClient extends AbstractPooledFileClient<FTPClient> {

    @Getter
    private final FtpFileClientProperties properties;

    public FtpFileClient(FtpFileClientProperties properties,
                         FilePathGenerator filePathGenerator,
                         FileMetadata defaultMetaData,
                         PooledObjectFactory<FTPClient> factory) {
        super(filePathGenerator, defaultMetaData, factory);
        this.properties = properties;
    }

    @Override
    protected GenericObjectPoolConfig<FTPClient> getPoolConfig() {
        return Util.convertFcc2Opc(properties.getPool());
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
                boolean makeDirectory = ftp.makeDirectory(str);
                boolean changeWorkingDirectory = ftp.changeWorkingDirectory(str);
                log.warn(str + "ftp client create dir success: {}; changeWorkDir：{}", makeDirectory, changeWorkingDirectory);
            }
        }
    }


    @Override
    protected FileObject doPutFile(FTPClient client, String part, InputStream content, String filepath, FileMetadata fileMetaData) throws Exception {
        String dir = getFileDirPath(part, filepath);
        createDirs(client, dir);
        boolean changeR = client.changeWorkingDirectory(dir);
        log.debug("change working dir result: {}", changeR);
        boolean result = client.storeFile(getFilename(part, filepath), content);
        log.debug("ftp upload result: {}", result);
        return new FtpFileObject(part, filepath, () -> {
            try {
                return getFileInputStream(client, part, filepath);
            } catch (Exception e) {
                throw new FileClientException("get file content error", e);
            }
        });
    }

    @Override
    protected Optional<FileObject> doGetFile(FTPClient client, String part, String filepath) {
        return Optional.of(new FtpFileObject(part, filepath, () -> getFileInputStream(part, filepath)));
    }

    private InputStream getFileInputStream(FTPClient client, String part, String filepath) throws Exception {
        client.changeWorkingDirectory(getFileDirPath(part, filepath));
        return client.retrieveFileStream(getFilename(part, filepath));
    }

    public InputStream getFileInputStream(String part, String filepath) {
        return template(client -> getFileInputStream(client, part, filepath), "get ftp file inputstream error");
    }

    @Override
    protected boolean doExists(FTPClient client, String part, String filepath) throws Exception {
        String[] names = client.listNames(getAbsFilepath(part, filepath));
        return names.length > 0;
    }

    @Override
    protected URI doGetUrl(FTPClient client, String part, String filepath) throws Exception {
        return new URI("ftp",
                properties.getUsername() + ":" + properties.getPassword(),
                properties.getHost(), properties.getPort(),
                getAbsFilepath(part, part),
                null, null);
    }

    @Override
    protected Void doDelete(FTPClient client, String part, String filepath) throws Exception {
        client.deleteFile(filepath);
        return null;
    }

    @Override
    protected String getDefaultPart() {
        return properties.getBaseDir();
    }

}
