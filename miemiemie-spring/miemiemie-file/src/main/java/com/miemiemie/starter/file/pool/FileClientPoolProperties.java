package com.miemiemie.starter.file.pool;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件客户端池配置
 *
 * @author yangshunxiang
 * @since 2023/2/24
 */
@Getter
@Setter
public class FileClientPoolProperties {

    private int maxTotal = 8;

    private int maxIdle = 8;

    private int minIdle = 0;

}
