package com.miemiemie.file.pathgen;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FilePathGenerator;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author yangshunxiang
 * @since 2023/2/26
 */
public class SimpleUuidFilepathGenerator implements FilePathGenerator {

    @Override
    public String generate(FileMetadata fileMetaData) {
        if (StringUtils.hasText(fileMetaData.get(FileMetadata.FILE_EXTENSION))) {
            return UUID.randomUUID() + "." + fileMetaData;
        }
        return UUID.randomUUID().toString();
    }
}
