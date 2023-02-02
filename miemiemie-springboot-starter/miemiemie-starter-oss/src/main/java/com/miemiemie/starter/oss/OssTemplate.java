package com.miemiemie.starter.oss;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * oss通用模板操作
 *
 * @author yangshunxiang
 * @since 2023/01/31
 */
@RequiredArgsConstructor
public class OssTemplate {

    private final AmazonS3 amazonS3;

    @Getter
    private final OssProperties ossProperties;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }

    /**
     * 获取全部bucket
     * <p>
     *
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/ListBuckets">AWS API Documentation</a>
     */
    public List<Bucket> listBuckets() {
        return amazonS3.listBuckets();
    }

    /**
     * 根据bucket名称获取一个Bucket
     *
     * @param bucketName bucket名称
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/ListBuckets">AWS API Documentation</a>
     */
    public Optional<Bucket> getBucket(String bucketName) {
        return amazonS3.listBuckets().stream().filter(b -> b.getName().equals(bucketName)).findFirst();
    }

    /**
     * 删除一个bucket
     *
     * @param bucketName bucket名称
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/DeleteBucket">AWS API Documentation</a>
     */
    public void deleteBucket(String bucketName) {
        amazonS3.deleteBucket(bucketName);
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectKey   文件名称
     * @param stream      文件流
     * @param contextType 类型
     * @see <a href= "http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/PutObject">AWS API Documentation</a>
     */
    public void putObject(String bucketName, String objectKey, InputStream stream, String contextType) {
        createBucket(bucketName);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contextType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectKey, stream, objectMetadata);
        amazonS3.putObject(putObjectRequest);
    }

    /**
     * 上传文件，使用默认的bucket
     *
     * @param objectKey   文件名称
     * @param stream      文件流
     * @param contextType 类型
     * @see <a href= "http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/PutObject">AWS API Documentation</a>
     */
    public void putObject(String objectKey, InputStream stream, String contextType) {
        putObject(ossProperties.getDefaultBucketName(), objectKey, stream, contextType);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectKey  文件名称
     * @param stream     文件流
     */
    public void putObject(String bucketName, String objectKey, InputStream stream) {
        putObject(bucketName, objectKey, stream, "application/octet-stream");
    }

    /**
     * 上传文件，使用默认的bucket
     *
     * @param objectKey 文件名称
     * @param stream    文件流
     */
    public void putObject(String objectKey, InputStream stream) {
        putObject(ossProperties.getDefaultBucketName(), objectKey, stream, "application/octet-stream");
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectKey  文件名称
     * @see <a href= "http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/GetObject">AWS API Documentation</a>
     */
    public S3Object getObject(String bucketName, String objectKey) {
        return amazonS3.getObject(bucketName, objectKey);
    }

    /**
     * 获取文件
     *
     * @param objectKey 文件名称
     * @return 文件对象
     */
    public S3Object getObject(String objectKey) {
        return amazonS3.getObject(ossProperties.getDefaultBucketName(), objectKey);
    }

    /**
     * 生成文件外链
     *
     * @param bucketName bucket名称
     * @param objectKey  文件名称
     * @param expires    过期时间，请注意该值必须小于7天
     * @param method     文件操作方法：GET（下载）、PUT（上传）
     * @return url
     * @see AmazonS3#generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method)
     */
    public String generatePresignedUrl(String bucketName, String objectKey, Duration expires, HttpMethod method) {
        Date expiration = Date.from(Instant.now().plus(expires));
        URL url = amazonS3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, objectKey).withMethod(method).withExpiration(expiration));
        return url.toString();
    }

    /**
     * 生成文件外链，位置在默认的bucket
     *
     * @param objectKey 文件名称
     * @param expires   过期时间，请注意该值必须小于7天
     * @param method    文件操作方法：GET（下载）、PUT（上传）
     * @return url
     * @see AmazonS3#generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method)
     */
    public String generatePresignedUrl(String objectKey, Duration expires, HttpMethod method) {
        return generatePresignedUrl(ossProperties.getDefaultBucketName(), objectKey, expires, method);
    }

    /**
     * 生成文件外链
     *
     * @param bucketName bucket
     * @param objectKey  文件名称
     * @param expires    过期时间，请注意该值必须小于7天
     * @return 默认返回一个get请求的url链接
     */
    public String generatePresignedUrl(String bucketName, String objectKey, Duration expires) {
        return generatePresignedUrl(bucketName, objectKey, expires, HttpMethod.GET);
    }

    /**
     * 生成文件外链，默认的bucket
     *
     * @param objectKey 文件名称
     * @param expires   过期时间，请注意该值必须小于7天
     * @return 默认返回一个get请求的url链接
     */
    public String generatePresignedUrl(String objectKey, Duration expires) {
        return generatePresignedUrl(ossProperties.getDefaultBucketName(), objectKey, expires, HttpMethod.GET);
    }

    /**
     * 列出所有文件
     *
     * @param bucketName bucket名称
     * @return 文件列表
     */
    public List<S3ObjectSummary> listObjects(String bucketName) {
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucketName);
        return listObjectsV2Result.getObjectSummaries();
    }

    /**
     * 列出所有文件，默认的bucket
     *
     * @return 文件列表
     */
    public List<S3ObjectSummary> listObjects() {
        return listObjects(ossProperties.getDefaultBucketName());
    }

    /**
     * 根据文件前缀查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @return 文件列表
     */
    public List<S3ObjectSummary> listObjectsByPrefix(String bucketName, String prefix) {
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucketName, prefix);
        return listObjectsV2Result.getObjectSummaries();
    }

    /**
     * 根据文件前缀查询文件，从默认的bucket中查询
     *
     * @param prefix 前缀
     * @return 文件列表
     */
    public List<S3ObjectSummary> listObjectsByPrefix(String prefix) {
        return listObjectsByPrefix(ossProperties.getDefaultBucketName(), prefix);
    }

    /**
     * 删除指定的文件
     *
     * @param bucketName bucket名称
     * @param objectKey  文件名称
     */
    public void deleteObject(String bucketName, String objectKey) {
        amazonS3.deleteObject(bucketName, objectKey);
    }

    /**
     * 从默认的bucket中删除指定文件
     *
     * @param objectKey 文件名称
     */
    public void deleteObject(String objectKey) {
        deleteObject(ossProperties.getDefaultBucketName(), objectKey);
    }

}
