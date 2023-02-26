package com.miemiemie.file;

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
