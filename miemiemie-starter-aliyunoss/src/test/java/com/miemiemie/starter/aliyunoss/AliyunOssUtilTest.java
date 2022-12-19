package com.miemiemie.starter.aliyunoss;

import com.aliyun.oss.OSS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author yangshunxiang
 * @since 2022/12/19
 */
@SpringBootTest(classes = AliyunOssUtilTest.class)
@EnableAutoConfiguration
public class AliyunOssUtilTest {


    public static final String BUCKET_NAME = "zhangdadashuai";

    @Test
    public void testGetOSSClient() {
        OSS ossClient = AliyunOssUtil.getOSSClient();
        Assertions.assertNotNull(ossClient);
    }

    @Test
    public void testUploadWithUrl() throws Exception {
        String objectName = "美女图片.jpg";
        AliyunOssUtil.upload(new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"), BUCKET_NAME, objectName);
        System.out.println(AliyunOssUtil.openUrl(BUCKET_NAME, objectName));
        System.out.println(AliyunOssUtil.internalUrl(BUCKET_NAME, objectName));
    }

    @Test
    public void testUploadWithStream() {
        String objectName = "abcdefg.txt";
        AliyunOssUtil.upload(new ByteArrayInputStream("abcdefg".getBytes(StandardCharsets.UTF_8)), BUCKET_NAME, objectName);
        System.out.println(AliyunOssUtil.openUrl(BUCKET_NAME, objectName));
        System.out.println(AliyunOssUtil.internalUrl(BUCKET_NAME, objectName));
    }

    @Test
    public void testUploadWithFile() {
        String objectName = "新建文本文档.txt";
        AliyunOssUtil.upload(new File("C:\\Users\\PC\\Desktop\\新建文本文档.txt"), BUCKET_NAME, objectName);
        System.out.println(AliyunOssUtil.openUrl(BUCKET_NAME, objectName));
        System.out.println(AliyunOssUtil.internalUrl(BUCKET_NAME, objectName));
    }

    @Test
    public void testList() {
        List<String> list = AliyunOssUtil.list(BUCKET_NAME);
        System.out.println(list);
    }
}
