package com.miemiemie.file;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.function.Supplier;

/**
 * 远程文件对象抽象
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
@Getter
@Setter
public class FileObject {

    /**
     * 文件所属组、所属bucket
     */
    private String part;

    /**
     * 远程文件实际存储路径
     */
    private String filepath;

    /**
     * 获取文件元信息
     */
    private FileMetadata fileMetadata = new FileMetadata();

    /**
     * 文件内容
     */
    private Supplier<InputStream> content;

    public static Builder builder() {
        return new Builder();

    }

    public static class Builder {

        private final FileObject fileObject = new FileObject();

        public Builder part(String part) {
            fileObject.setPart(part);
            return this;
        }

        public Builder filepath(String filepath) {
            fileObject.setFilepath(filepath);
            return this;
        }

        public Builder contentSupplier(Supplier<InputStream> supplier) {
            fileObject.setContent(supplier);
            return this;
        }

        public Builder metadata(FileMetadata fileMetadata) {
            fileObject.setFileMetadata(fileMetadata);
            return this;
        }

        public FileObject build() {
            return fileObject;
        }

    }

}
