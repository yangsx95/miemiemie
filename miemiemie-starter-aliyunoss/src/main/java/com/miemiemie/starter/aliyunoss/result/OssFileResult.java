package com.miemiemie.starter.aliyunoss.result;

import lombok.Data;
import org.springframework.boot.SpringApplication;

/**
 * @author yangshunxiang
 * @since 2022/12/16
 */
@Data
public class OssFileResult {

    /**
     * 内网url
     */
    private String internalUrl;

    /**
     * 公网url
     */
    private String url;

    /**
     * 文件大小（单位byte）
     */
    private Long fileSize;

    public static class Builder {

        /**
         * object key
         */
        private String objectKey;

        /**
         * 文件大小（单位byte）
         */
        private long fileSize;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public void objectKey(String objectKey) {
            this.objectKey = objectKey;
        }

        public void fileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        public OssFileResult build() {
            OssFileResult ossFileResult = new OssFileResult();
            ossFileResult.setFileSize(this.fileSize);
            return ossFileResult;
        }

    }
}
