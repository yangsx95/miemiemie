package com.miemiemie.starter.oss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.time.Duration;

/**
 * @author yangshunxiang
 * @since 2022/12/19
 */
@SpringBootTest(classes = OssFileTest.class)
@EnableAutoConfiguration
public class OssFileTest {

    public static final String BUCKET_NAME = "file-bed";

    @Test
    public void testUploadUseDefaultBucketAndBaseDir() throws Exception {
        OssFile ossFile = OssFileBuilder.builder()
                .filename("美女图片.jpg")
                .build();
        ossFile.upload(new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500").openStream());
        System.out.println(ossFile.presignedUrl(Duration.ofDays(1)));
    }

    @Test
    public void testUploadUseSpecifyBucketAndBaseDirAndDir() throws Exception {
        OssFile ossFile = OssFileBuilder.builder()
                .bucketName(BUCKET_NAME)
                .appendDir("image")
                .appendDir("美女")
                .filename("美女图片.jpg")
                .build();
        ossFile.upload(new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500").openStream());
        System.out.println(ossFile.presignedUrl(Duration.ofDays(1)));
    }

    @Test
    public void testUploadUseRandomDirAndFilename() throws Exception {
        OssFile ossFile = OssFileBuilder.builder()
                .bucketName(BUCKET_NAME)
                .randomDir()
                .randomFilename(".jpg")
                .build();
        ossFile.upload(new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500").openStream());
        System.out.println(ossFile.presignedUrl(Duration.ofDays(1)));
    }

}
