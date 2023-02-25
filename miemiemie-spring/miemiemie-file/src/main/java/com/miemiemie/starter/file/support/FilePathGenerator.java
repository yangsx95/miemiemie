package com.miemiemie.starter.file.support;

import com.miemiemie.starter.file.FileMetadata;

/**
 * 文件路径生成
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
@FunctionalInterface
public interface FilePathGenerator {

    String generate(FileMetadata fileMetaData);

}
