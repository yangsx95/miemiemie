package com.miemiemie.starter.file.support.ftp;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.exception.FileClientException;
import com.miemiemie.starter.file.support.AbstractFileClient;
import com.miemiemie.starter.file.support.FilePathGenerator;
import com.miemiemie.starter.file.util.Util;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

/**
 * ftp文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
public class FtpFileClient extends AbstractFileClient {

    private GenericObjectPool<FTPClient> pool;

    public FtpFileClient(FtpFileClientProperties ftpFileClientProperties,
                         FtpClientFactory ftpClientFactory,
                         FilePathGenerator filePathGenerator,
                         FileMetadata defaultMetaData) {
        super(filePathGenerator, defaultMetaData);
        pool = new GenericObjectPool<>(ftpClientFactory, Util.convertFcc2Opc(ftpFileClientProperties.getPool()));
    }

    @Override
    public FileObject putFile(String part, InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {

        return null;
    }

    @Override
    public Optional<FileObject> getFile(String part, String filepath) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String part, String filepath) {
        return false;
    }

    @Override
    public URI getUrl(String part, String filepath) {
        return null;
    }

    @Override
    public void deleteFile(String part, String filepath) throws FileClientException {

    }

    @Override
    protected String getDefaultPart() {
        return null;
    }
}
