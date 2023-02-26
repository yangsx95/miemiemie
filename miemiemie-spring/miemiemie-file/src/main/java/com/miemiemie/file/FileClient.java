package com.miemiemie.file;

import com.miemiemie.file.exception.FileClientException;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

/**
 * 文件客户端抽象
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public interface FileClient {

    /**
     * 上传文件
     *
     * @param part         文件所属组、bucket
     * @param content      文件内容
     * @param fileMetaData 文件元信息
     * @param filepath     指定文件存储路径
     * @throws FileClientException 上传失败
     */
    FileObject putFile(String part, InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException;

    /**
     * 上传文件，不指定存储路径
     *
     * @param part         文件所属组、bucket
     * @param content      文件内容
     * @param fileMetaData 文件元信息
     * @throws FileClientException 上传失败
     */
    FileObject putFile(String part, InputStream content, FileMetadata fileMetaData) throws FileClientException;

    /**
     * 上传文件，不指定元信息
     *
     * @param part     文件所属组、bucket
     * @param content  文件内容
     * @param filepath 指定文件存储路径
     * @throws FileClientException 上传失败
     */
    FileObject putFile(String part, InputStream content, String filepath) throws FileClientException;

    /**
     * 上传文件到默认的part
     *
     * @param content      文件内容
     * @param fileMetaData 文件元信息
     * @param filepath     指定文件存储路径
     * @throws FileClientException 上传失败
     */
    FileObject putFile(InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException;

    /**
     * 上传文件到默认的part，不指定存储路径
     *
     * @param content      文件内容
     * @param fileMetaData 文件元信息
     * @throws FileClientException 上传失败
     */
    FileObject putFile(InputStream content, FileMetadata fileMetaData) throws FileClientException;

    /**
     * 上传文件到默认的part，不指定元信息
     *
     * @param content  文件内容
     * @param filepath 指定文件存储路径
     * @throws FileClientException 上传失败
     */
    FileObject putFile(InputStream content, String filepath) throws FileClientException;

    /**
     * 上传文件到指定的part，不指定存储路径，不指定文件元信息
     *
     * @param content 文件内容
     * @return 上传的文件对象
     * @throws FileClientException 上传失败
     */
    FileObject putFile(InputStream content) throws FileClientException;

    /**
     * 获取一个远程文件对象
     *
     * @param filepath 存储路径
     * @return 返回远程文件对象
     */
    Optional<FileObject> getFile(String filepath);

    /**
     * 从指定的part中获取远程文件对象
     *
     * @param part     文件所属组、bucket
     * @param filepath 存储路径
     * @return 返回远程文件对象
     */
    Optional<FileObject> getFile(String part, String filepath);

    /**
     * 判断文件是否存在
     *
     * @param part     文件所属组、bucket
     * @param filepath 存储路径
     * @return true代表文件在文件服务器中存在，false代表不存在
     */
    boolean exists(String part, String filepath);

    /**
     * 判断文件是否存在
     *
     * @param filepath 文件所属组、bucket
     * @return true代表文件在文件服务器中存在，false代表不存在
     */
    boolean exists(String filepath);

    /**
     * 获取文件url
     *
     * @param filepath 存储路径
     * @return url
     */
    URI getUrl(String filepath);

    /**
     * 获取文件url
     *
     * @param part     文件所属组、bucket
     * @param filepath 存储路径
     * @return url
     */
    URI getUrl(String part, String filepath);

    /**
     * 删除文件
     *
     * @param part     文件所属组、bucket
     * @param filepath 存储路径
     * @throws FileClientException 删除出错
     */
    void deleteFile(String part, String filepath) throws FileClientException;

    /**
     * 删除文件
     *
     * @param filepath 存储路径
     * @throws FileClientException 删除出错
     */
    void deleteFile(String filepath) throws FileClientException;

}
