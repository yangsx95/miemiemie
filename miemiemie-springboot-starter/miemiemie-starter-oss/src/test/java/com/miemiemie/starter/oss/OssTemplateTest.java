package com.miemiemie.starter.oss;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * @author yangshunxiang
 * @since 2023/1/1
 */
@SpringBootTest(classes = OssTemplateTest.class)
@EnableAutoConfiguration
public class OssTemplateTest {

    public static final String BUCKET_NAME = "yangdashuai";

    @Resource
    private OssTemplate ossTemplate;


    @BeforeEach
    @Test
    public void testCreateBucket() {
        ossTemplate.createBucket(BUCKET_NAME);
    }

    @Test
    public void testListBuckets() {
        List<Bucket> buckets = ossTemplate.listBuckets();
        Assertions.assertTrue(buckets.size() > 0);
        System.out.println(buckets);
    }

    @Test
    public void testGetBucket() {
        Optional<Bucket> test = ossTemplate.getBucket(BUCKET_NAME);
        Assertions.assertTrue(test.isPresent());
        Assertions.assertEquals(test.get().getName(), BUCKET_NAME);
    }

    @Test
    public void testDeleteBucket() {
        ossTemplate.deleteBucket(BUCKET_NAME);
        Assertions.assertFalse(ossTemplate.getBucket(BUCKET_NAME).isPresent());
    }

    @Test
    public void testPutObject() throws IOException {
        ossTemplate.putObject(BUCKET_NAME,
                "girl.jpg",
                new URL("https://gd-hbimg.huaban.com/f05ba7abf1f8541d774746517444927b4fc4a1522d48a-wp3u2Y_fw658").openConnection().getInputStream(),
                "image/jpg"
        );
    }

    @Test
    public void testPutObject2() throws IOException {
        ossTemplate.putObject(BUCKET_NAME,
                "sex.jpg",
                new URL("https://img0.baidu.com/it/u=2953940086,3621245794&fm=253&fmt=auto&app=138&f=JPEG").openConnection().getInputStream()
        );
    }

    @Test
    public void testGetObject() {
        S3Object girl = ossTemplate.getObject(BUCKET_NAME, "girl.jpg");
        Assertions.assertNotNull(girl);
    }

    @Test
    public void testGeneratePresignedUrl() {
        String url = ossTemplate.generatePresignedUrl(BUCKET_NAME, "girl.jpg", Duration.ofSeconds(30), HttpMethod.GET);
        System.out.println(url);
    }

    @Test
    public void testListObjects() {
        List<S3ObjectSummary> objects = ossTemplate.listObjects(BUCKET_NAME);
        System.out.println(objects);
    }

    @Test
    public void testListObjectsByPrefix() {
        List<S3ObjectSummary> objects = ossTemplate.listObjectsByPrefix(BUCKET_NAME, "gi");
        System.out.println(objects);
    }

    @Test
    public void testDeleteObject() {
        ossTemplate.deleteObject(BUCKET_NAME, "girl.jpg");
    }
}
