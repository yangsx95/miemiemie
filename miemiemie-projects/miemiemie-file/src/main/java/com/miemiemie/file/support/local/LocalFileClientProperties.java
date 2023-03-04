package com.miemiemie.file.support.local;

import lombok.Getter;
import lombok.Setter;

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
public class LocalFileClientProperties {

    public static final LocalFileClientProperties DEFAULT = new LocalFileClientProperties();

    /**
     * 文件存储的根路径
     */
    private String baseDir = Paths.get(File.separator, "data").toAbsolutePath().toString();

}
