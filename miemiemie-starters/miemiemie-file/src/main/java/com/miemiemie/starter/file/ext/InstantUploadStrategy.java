package com.miemiemie.starter.file.ext;

import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 秒传策略
 *
 * @author yangshunxiang
 * @since 2023/3/4
 */
public interface InstantUploadStrategy {

    /**
     * 根据文件唯一值(比如hash)判断文件是否已经上传
     *
     * @param key 文件唯一标识
     * @return 如果文件已经上传，将返回文件信息，否则返回空
     */
    Optional<FileObject> existAndGet(String key);

    /**
     * 记录一笔上传成功的文件，用以下次秒传
     *
     * @param fileObject 文件对象
     */
    void record(String key, FileObject fileObject);

    /**
     * 删除一个已经记录的文件
     *
     * @param key 文件唯一标识
     */
    void clear(String key, FileObject fileObject);

    /**
     * 从元数据中获取文件内容唯一值
     *
     * @return 字段名
     */
    default Optional<String> getKeyFromMetaData(FileMetadata fileMetadata) {
        String md5 = fileMetadata.get(FileMetadata.MD5);
        if (!StringUtils.hasText(md5)) {
            return Optional.empty();
        }
        return Optional.of(md5);
    }

}
