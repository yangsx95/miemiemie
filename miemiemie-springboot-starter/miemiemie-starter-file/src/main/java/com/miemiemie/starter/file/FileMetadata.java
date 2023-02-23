package com.miemiemie.starter.file;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

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
@FieldNameConstants
public class FileMetadata {

    private Map<String, String> otherMetadata = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 文件内容类型
     */
    private String contentType;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 添加一个
     */
    public void put(String key, String value) {
        otherMetadata.put(key, value);
    }
}
