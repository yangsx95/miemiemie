package com.miemiemie.starter.file;

import com.miemiemie.core.util.MimeTypes;
import com.miemiemie.starter.file.util.Util;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * 文件元信息
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
@Getter
@Setter
public class FileMetadata extends TreeMap<String, String> {

    public static String FILE_NAME = "FileName";

    public static String FILE_EXTENSION = "FileExtension";

    public static String FILE_SIZE_BYTE = "fileSizeByte";

    public static String CONTENT_TYPE = "ContentType";

    public FileMetadata() {
        super(String.CASE_INSENSITIVE_ORDER);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final FileMetadata fileMetadata = new FileMetadata();

        public Builder ofFilepath(String filepath) {
            String filename = Util.getFilename("", filepath);
            String fileExtension = Util.getFileExtension(filename);
            fileMetadata.put(FILE_NAME, filename);
            fileMetadata.put(FILE_EXTENSION, fileExtension);
            fileMetadata.put(CONTENT_TYPE, MimeTypes.getMimeType(fileExtension));
            return this;
        }

        public Builder filename(String filename) {
            fileMetadata.put(FILE_NAME, filename);
            return this;
        }

        public Builder ofFilename(String filename) {
            String fileExtension = Util.getFileExtension(filename);
            fileMetadata.put(FILE_NAME, filename);
            fileMetadata.put(FILE_EXTENSION, fileExtension);
            fileMetadata.put(CONTENT_TYPE, MimeTypes.getMimeType(fileExtension));
            return this;
        }

        public Builder fileExtension(String extension) {
            fileMetadata.put(FILE_EXTENSION, extension);
            return this;
        }

        public Builder ofFileExtension(String extension) {
            fileMetadata.put(FILE_EXTENSION, extension);
            fileMetadata.put(CONTENT_TYPE, MimeTypes.getMimeType(extension));
            return this;
        }

        public Builder fileSizeByte(long size) {
            fileMetadata.put(FILE_SIZE_BYTE, String.valueOf(size));
            return this;
        }

        public Builder contentType(String contentType) {
            fileMetadata.put(CONTENT_TYPE, contentType);
            return this;
        }

        public Builder ofFile(File file) {
            String fileName = file.getName();
            String fileExtension = Util.getFileExtension(fileName);
            fileMetadata.put(FILE_NAME, fileName);
            fileMetadata.put(FILE_EXTENSION, fileExtension);
            fileMetadata.put(CONTENT_TYPE, MimeTypes.getMimeType(fileExtension));
            fileMetadata.put(FILE_SIZE_BYTE, String.valueOf(file.length()));
            return this;
        }

        public Builder putAll(Map<String, String> metadata) {
            fileMetadata.putAll(metadata);
            return this;
        }

        public FileMetadata build() {
            return fileMetadata;
        }

    }
}
