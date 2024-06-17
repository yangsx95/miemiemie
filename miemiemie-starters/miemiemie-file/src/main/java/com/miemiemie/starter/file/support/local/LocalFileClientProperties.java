package com.miemiemie.starter.file.support.local;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.nio.file.Paths;

/**
 * 本地文件客户端配置
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "miemiemie.file.local")
public class LocalFileClientProperties {

    public static final LocalFileClientProperties DEFAULT = new LocalFileClientProperties();

    /**
     * 文件存储的根路径
     */
    private String baseDir = Paths.get(File.separator, "data").toAbsolutePath().toString();

}
