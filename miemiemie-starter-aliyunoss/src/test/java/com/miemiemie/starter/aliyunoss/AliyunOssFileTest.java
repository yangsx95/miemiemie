package com.miemiemie.starter.aliyunoss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

/**
 * @author yangshunxiang
 * @since 2022/12/19
 */
@SpringBootTest(classes = AliyunOssFileTest.class)
@EnableAutoConfiguration
public class AliyunOssFileTest {

    public static final String BUCKET_NAME = "file-bed";

    @Test
    public void testUploadUseDefaultBucketAndBaseDir() throws Exception {
        AliyunOssFile ossFile = AliyunOssFileBuilder.builder()
                .filename("美女图片.jpg")
                .build();
        ossFile.upload(new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500").openStream());
        System.out.println(ossFile.openUrl());
        System.out.println(ossFile.internalUrl());
    }

    @Test
    public void testUploadUseSpecifyBucketAndBaseDirAndDir() throws Exception {
        AliyunOssFile ossFile = AliyunOssFileBuilder.builder()
                .bucketName(BUCKET_NAME)
                .baseDir("image")
                .dir("美女")
                .filename("美女图片.jpg")
                .build();
        ossFile.upload(new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500").openStream());
        System.out.println(ossFile.openUrl());
        System.out.println(ossFile.internalUrl());
    }

    @Test
    public void testUploadUseRandomDirAndFilename() throws Exception {
        AliyunOssFile ossFile = AliyunOssFileBuilder.builder()
                .bucketName(BUCKET_NAME)
                .randomDir()
                .randomFilename(".jpg")
                .build();
        ossFile.upload(new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500").openStream());
        System.out.println(ossFile.openUrl());
        System.out.println(ossFile.internalUrl());
    }

}
