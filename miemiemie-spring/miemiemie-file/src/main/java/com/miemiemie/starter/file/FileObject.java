package com.miemiemie.starter.file;

import java.io.InputStream;
import java.util.function.Supplier;

/**
 * 远程文件对象抽象
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public interface FileObject {

    /**
     * 文件所属组、所属bucket
     *
     * @return part
     */
    String getPart();

    /**
     * 远程文件实际存储路径
     *
     * @return 文件路径
     */
    String getFilePath();

    /**
     * 获取文件元信息
     *
     * @return 元信息
     */
    FileMetadata getMetaData();

    /**
     * 获取文件内容
     *
     * @return 文件内容流
     */
    Supplier<InputStream> getContent();
}
