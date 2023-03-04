package com.miemiemie.starter.file;

import com.miemiemie.file.support.fastdfs.FastDfsFileClientProperties;
import com.miemiemie.file.support.ftp.FtpFileClientProperties;
import com.miemiemie.file.support.local.LocalFileClientProperties;
import com.miemiemie.file.support.s3.S3FileClientProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangshunxiang
 * @since 2023/3/2
 */
@Data
@ConfigurationProperties(value = "miemiemie.file")
public class FileClientProperties {

    private LocalFileClientProperties local = new LocalFileClientProperties();

    private FtpFileClientProperties ftp = new FtpFileClientProperties();

    private FastDfsFileClientProperties fastDfs = new FastDfsFileClientProperties();

    private S3FileClientProperties s3 = new S3FileClientProperties();

}
