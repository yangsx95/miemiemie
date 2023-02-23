package com.miemiemie.starter.file.support;

import com.miemiemie.starter.file.FileMetadata;

@FunctionalInterface
public interface FilePathGenerator {

    String generate(FileMetadata fileMetaData);

}
