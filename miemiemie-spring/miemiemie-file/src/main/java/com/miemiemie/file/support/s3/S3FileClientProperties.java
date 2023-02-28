package com.miemiemie.file.support.s3;

import lombok.Getter;
import lombok.Setter;

/**
 * s3文件客户端配置
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
@Getter
@Setter
public class S3FileClientProperties {

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
