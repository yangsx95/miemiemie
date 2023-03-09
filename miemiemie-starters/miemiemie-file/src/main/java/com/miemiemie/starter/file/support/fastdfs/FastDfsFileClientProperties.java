package com.miemiemie.starter.file.support.fastdfs;

import com.miemiemie.starter.file.pool.FileClientPoolProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yangshunxiang
 * @since 2023/2/25
 */
@Getter
@Setter
public class FastDfsFileClientProperties {

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
