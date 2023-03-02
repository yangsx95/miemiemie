package com.miemiemie.starter.file;

import com.amazonaws.services.s3.AmazonS3;
import com.miemiemie.file.FilePathGenerator;
import com.miemiemie.file.pathgen.EmptyFilepathGenerator;
import com.miemiemie.file.pathgen.SimpleUuidFilepathGenerator;
import com.miemiemie.file.support.fastdfs.FastDfsFileClient;
import com.miemiemie.file.support.ftp.FtpFileClient;
import com.miemiemie.file.support.local.LocalFileClient;
import com.miemiemie.file.support.s3.S3FileClient;
import org.apache.commons.net.ftp.FTPFile;
import org.csource.fastdfs.TrackerClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 文件客户端自动配置
 *
 * @author yangshunxiang
 * @since 2023/3/2
 */
@Configuration
@ComponentScan(basePackageClasses = FileAutoConfiguration.class)
@EnableConfigurationProperties
public class FileAutoConfiguration {

    @ConditionalOnMissingBean(FilePathGenerator.class)
    @Bean
    public FilePathGenerator filePathGenerator() {
        return new SimpleUuidFilepathGenerator();
    }

    @ConditionalOnProperty("miemiemie.file.local")
    @ConditionalOnMissingBean(LocalFileClient.class)
    @Bean
    public LocalFileClient localFileClient(FileClientProperties properties, FilePathGenerator filePathGenerator) {
        return new LocalFileClient(properties.getLocal(), filePathGenerator);
    }

    @ConditionalOnProperty("miemiemie.file.ftp")
    @ConditionalOnMissingBean(FtpFileClient.class)
    @ConditionalOnClass(FTPFile.class)
    @Bean
    public FtpFileClient ftpFileClient(FileClientProperties properties, FilePathGenerator filePathGenerator) {
        return new FtpFileClient(properties.getFtp(), filePathGenerator);
    }

    @ConditionalOnProperty("miemiemie.file.fast-dfs")
    @ConditionalOnMissingBean(FastDfsFileClient.class)
    @ConditionalOnClass(TrackerClient.class)
    @Bean
    public FastDfsFileClient fastDfsFileClient(FileClientProperties properties) {
        return new FastDfsFileClient(properties.getFastDfs(), new EmptyFilepathGenerator());
    }

    @ConditionalOnProperty("miemiemie.file.s3")
    @ConditionalOnMissingBean(S3FileClient.class)
    @ConditionalOnClass(AmazonS3.class)
    @Bean
    public S3FileClient s3FileClient(FileClientProperties properties, FilePathGenerator filePathGenerator) {
        return new S3FileClient(properties.getS3(), filePathGenerator);
    }
}
