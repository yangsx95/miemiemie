package com.miemiemie.starter.file;

import com.miemiemie.starter.file.pool.FileClientPoolProperties;
import com.miemiemie.starter.file.support.local.LocalFileClient;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.nio.file.Paths;

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

    /**
     * @author yangshunxiang
     * @since 2023/2/25
     */
    @Getter
    @Setter
    public static class FastDfsFileClientProperties {

        private boolean enabled = false;

        private int connectTimeoutInSeconds = 30;

        private int networkTimeoutInSeconds = 60;

        private String charset = "UTF-8";

        private String httpAntiStealToken = "";

        private String httpSecretKey = "";

        private int httpTrackerHttpPort = 8888;

        private String trackerServers = "127.0.0.1:22122";

        private String dataAccessHost = "http://127.0.0.1";

        private FileClientPoolProperties pool = new FileClientPoolProperties();
    }

    /**
     * ftp文件客户端配置类
     *
     * @author yangshunxiang
     * @since 2023/2/24
     */
    @Getter
    @Setter
    public static class FtpFileClientProperties {

        private boolean enabled = false;

        /**
         * 主机名
         */
        private String host;

        /**
         * 端口
         */
        private int port = 21;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * ftp 连接超时时间 毫秒
         */
        private int connectTimeOut = 5000;

        private String controlEncoding = "utf-8";

        /**
         * 缓冲区大小
         */
        private int bufferSize = 1024;

        /**
         * 传输数据格式 2表binary二进制数据
         */
        private int fileType = 2;

        private int dataTimeout = 120000;

        private boolean useEPSVwithIPv4 = false;

        /**
         * 是否启用被动模式
         */
        private boolean passiveMode = true;

        /**
         * 默认工作路径
         */
        private String baseDir = File.separator;

        /**
         * 连接池配置
         */
        private FileClientPoolProperties pool = new FileClientPoolProperties();


    }

    /**
     * 本地文件客户端配置
     *
     * @author yangshunxiang
     * @since 2023/2/23
     */
    @Getter
    @Setter
    @Validated
    @NoArgsConstructor
    public static class LocalFileClientProperties {

        public static final String DEFAULT_PATH = System.getProperties().get("user.home").toString() + File.separator + ".data";

        public static final LocalFileClientProperties DEFAULT = new LocalFileClientProperties(DEFAULT_PATH);

        public LocalFileClientProperties(String baseDir) {
            this.baseDir = baseDir;
        }

        private boolean enabled = false;

        /**
         * 文件存储的根路径
         */
        private String baseDir;

    }

    /**
     * s3文件客户端配置
     *
     * @author yangshunxiang
     * @since 2023/2/23
     */
    @Getter
    @Setter
    public static class S3FileClientProperties {

        private boolean enabled = false;

        /**
         * oss 服务端点，也是url
         */
        private String endpoint;

        /**
         * 指定请求路径形式是否为 path-style。如果为false，那么路径形式为 virtual-hosted-style。阿里云需要配置为false。
         * 参考：<a href="https://stackoverflow.com/questions/46839596/generate-s3-url-in-path-style-format">stackoverflow Generate S3 URL in "path-style" format</a>
         */
        private boolean pathStyleAccess = true;

        /**
         * 区域
         */
        private String region;

        /**
         * access key
         */
        private String accessKey;

        /**
         * secret key
         */
        private String secretKey;

        /**
         * 默认的bucket名称
         */
        private String defaultBucketName = "public";

    }
}
