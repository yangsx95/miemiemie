package com.miemiemie.starter.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.github.f4b6a3.ulid.Ulid;
import com.miemiemie.core.enums.ResultStatusEnum;
import com.miemiemie.core.exception.BizException;
import com.miemiemie.core.util.SpringContextHolder;
import com.miemiemie.starter.aliyunoss.config.AliyunOssConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AliyunOssUtil {

    /**
     * 获取oss客户端
     *
     * @return OSSClient oss客户端
     */
    public static @NonNull OSS getOSSClient() {
        OSS ossClient = SpringContextHolder.getBean(OSS.class);
        Assert.notNull(ossClient, "未获取到OSSClient实例");
        return ossClient;
    }

    public static @NonNull String objectKey(String dir, String fileName) {
        Assert.isTrue(StringUtils.hasText(fileName), "文件名称不能为空");
        dir = Optional.ofNullable(dir)
                .filter(e -> !StringUtils.hasText(e))
                .map(e -> e.replace("\\", "/"))
                .orElse("/");
        return dir + "/" + fileName;
    }

    public static @NonNull String objectKey(String dir, File file) {
        Assert.notNull(file, "文件不能为空");
        return objectKey(dir, file.getName());
    }

    public static @NonNull String randomObjectKeyByExt(String dir, String fileExtension) {
        if (!StringUtils.hasText(fileExtension)) {
            return objectKey(dir, Ulid.fast().toString());
        }

        fileExtension = fileExtension.trim();
        if (!fileExtension.startsWith(".")) {
            fileExtension = "." + fileExtension;
        }
        return objectKey(dir, Ulid.fast() + fileExtension);
    }

    public static @NonNull String randomObjectKeyByFilename(String dir, String filename) {
        Assert.isTrue(StringUtils.hasText(filename), "文件名称不能为空");
        String fileExt = null;
        if (filename.contains(".")) {
            fileExt = filename.substring(filename.lastIndexOf("."));
        }
        return randomObjectKeyByExt(dir, fileExt);
    }

    public static @NonNull String randomObjectKey(String dir, File file) {
        Assert.notNull(file, "文件不能为空");
        String fileName = file.getName();
        String fileExtension = null;
        if (fileName.contains(".")) {
            fileExtension = fileName.substring(fileName.lastIndexOf("."));
        }
        return objectKey(dir, fileExtension);
    }

    /**
     * 文件上传
     *
     * @param url        URL
     * @param bucketName bucket名称
     * @param objectName 上传文件目录和（包括文件名）例如“test/index.html”
     */
    public static void upload(URL url, String bucketName, String objectName) {
        OSS ossClient = getOSSClient();
        try {
            InputStream inputStream = url.openStream();
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            log.error("上传文件到oss失败", e);
            throw new BizException(ResultStatusEnum.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 文件上传
     *
     * @param inputStream 输入流
     * @param bucketName  bucket名称
     * @param objectName  上传文件目录和（包括文件名） 例如“test/a.jpg”
     */
    public static void upload(InputStream inputStream, String bucketName, String objectName) {
        OSS ossClient = getOSSClient();
        try {
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            log.error("上传文件到oss失败", e);
            throw new BizException(ResultStatusEnum.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 文件上传
     *
     * @param file       上传的文件
     * @param bucketName bucket名称
     * @param objectName 上传文件目录和（包括文件名） 例如“test/a.jpg”
     */
    public static void upload(File file, String bucketName, String objectName) {
        OSS ossClient = getOSSClient();
        try {
            ossClient.putObject(bucketName, objectName, file);
        } catch (Exception e) {
            log.error("上传文件到oss失败", e);
            throw new BizException(ResultStatusEnum.FILE_UPLOAD_ERROR);
        }
    }

    public static String internalUrl(String bucketName, String objectKey) {
        String url = url(bucketName, objectKey);
        if (url.contains("internal")) {
            return url;
        } else {
            return url.replace(".aliyuncs.com", "-internal.aliyuncs.com");
        }
    }

    public static String openUrl(String bucketName, String objectKey) {
        String url = url(bucketName, objectKey);
        if (url.contains("internal")) {
            return url.replace("-internal.aliyuncs.com", ".aliyuncs.com");
        } else {
            return url;
        }
    }

    public static String url(String bucketName, String objectKey) {
        Assert.isTrue(StringUtils.hasText(bucketName), "bucketName不能为空");
        Assert.isTrue(StringUtils.hasText(objectKey), "objectKey不能为空");
        AliyunOssConfig config = SpringContextHolder.getBean(AliyunOssConfig.class);
        Assert.notNull(config, "未找到oss配置");
        return bucketName.trim() + "." + config.getEndpoint() + "/" + objectKey.trim();
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param key        文件路径/名称，例如“test/a.txt”
     */
    public static void deleteFile(String bucketName, String key) {
        getOSSClient().deleteObject(bucketName, key);
    }

    /**
     * 获取oss文件流
     *
     * @param bucketName bucket名称
     * @param key        文件路径和名称
     * @return InputStream    文件输入流
     */
    public static InputStream download(String bucketName, String key) {
        OSS ossClient = getOSSClient();

        InputStream content;
        try {
            OSSObject ossObj = ossClient.getObject(bucketName, key);
            content = ossObj.getObjectContent();
        } catch (Exception e) {
            log.error("获取oss文件失败", e);
            throw new BizException(ResultStatusEnum.FILE_DOWNLOAD_ERROR);
        }
        return content;
    }

    /**
     * 获取文件列表
     *
     * @param bucketName bucket名称
     * @return List<String>  文件路径和大小集合
     */
    public static List<String> list(String bucketName) {
        OSS ossClient = getOSSClient();

        List<String> results = new ArrayList<>();
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                results.add(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
        } catch (Exception e) {
            log.error("读取oss文件列表失败", e);
            throw new BizException(ResultStatusEnum.BIZ_ERROR.getCode(), "读取oss文件列表失败");
        }
        return results;
    }
}

