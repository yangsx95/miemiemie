package com.miemiemie.file.pathgen;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FilePathGenerator;

/**
 * 空文件路径生成器
 *
 * @author yangshunxiang
 * @since 2023/2/26
 */
public class EmptyFilepathGenerator implements FilePathGenerator {

    @Override
    public String generate(FileMetadata fileMetaData) {
        return "";
    }
}
