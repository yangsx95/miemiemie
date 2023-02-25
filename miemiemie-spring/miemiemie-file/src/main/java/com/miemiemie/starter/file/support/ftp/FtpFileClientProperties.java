package com.miemiemie.starter.file.support.ftp;

import com.miemiemie.starter.file.pool.FileClientPoolProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * ftp文件客户端配置类
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
@Getter
@Setter
public class FtpFileClientProperties {

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
