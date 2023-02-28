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
        String ext = fileMetaData.get(FileMetadata.FILE_EXTENSION);
        if (StringUtils.hasText(ext)) {
            return UUID.randomUUID() + "." + fileMetaData.get(ext.trim());
        }
        return UUID.randomUUID().toString();
    }
}
