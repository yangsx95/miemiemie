package com.miemiemie.starter.file.support.ftp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * ftp文件客户端工厂
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
@Slf4j
public class FtpClientFactory extends BasePooledObjectFactory<FTPClient> {

    @Getter
    private final FtpFileClientProperties ftpFileClientProperties;

    public FtpClientFactory(FtpFileClientProperties ftpFileClientProperties) {
        this.ftpFileClientProperties = ftpFileClientProperties;
    }

    @Nullable
    @Override
    public FTPClient create() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(ftpFileClientProperties.getConnectTimeOut());
        try {
            log.info("prepare connect ftp: {}", ftpFileClientProperties.getHost() + ":" + ftpFileClientProperties.getPort());
            ftpClient.connect(ftpFileClientProperties.getHost(), ftpFileClientProperties.getPort());

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("FTPServer connect refused");
                return null;
            }

            boolean result = ftpClient.login(ftpFileClientProperties.getUsername(), ftpFileClientProperties.getPassword());
            if (!result) {
                log.error("ftpClient login error! {}", ftpFileClientProperties.getHost() + ":" + ftpFileClientProperties.getPort());
                throw new Exception("ftpClient login error!  " +
                        "username:" + ftpFileClientProperties.getUsername() + ", " +
                        "password:" + ftpFileClientProperties.getPassword()
                );
            }

            ftpClient.setControlEncoding(ftpFileClientProperties.getControlEncoding());
            ftpClient.setBufferSize(ftpFileClientProperties.getBufferSize());
            ftpClient.setFileType(ftpFileClientProperties.getFileType());
            ftpClient.setDataTimeout(ftpFileClientProperties.getDataTimeout());
            ftpClient.setUseEPSVwithIPv4(ftpFileClientProperties.isUseEPSVwithIPv4());
            if (ftpFileClientProperties.isPassiveMode()) {
                log.info("ftp client enter local passive mode");
                ftpClient.enterLocalPassiveMode();
            }
        } catch (IOException e) {
            log.error("ftp connect error：", e);
        }
        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<>(ftpClient);
    }

    // 销毁对象
    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        ftpClient.logout();
        super.destroyObject(p);
        log.debug("ftp client destroy {}", p.getObject());
    }

    // 校验对象
    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = p.getObject();
        boolean connect = false;
        try {
            connect = ftpClient.sendNoOp();
            log.debug("ftp client is available：" + connect);
        } catch (IOException e) {
            log.warn("ftp client is not available", e);
        }
        return connect;
    }

}
