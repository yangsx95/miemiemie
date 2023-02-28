package com.miemiemie.file.support.s3;

import cn.hutool.core.io.FileUtil;
import com.amazonaws.services.s3.model.Bucket;
import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.FilePathGenerator;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yangshunxiang
 * @since 2023/2/27
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class S3FileClientTest {

    public static final String ENDPOINT = "http://127.0.0.1:9000";
    private static S3FileClient s3FileClient;

    public static final String bucket = "s3clienttest";

    private static final String filepathNameTxt = "a/name.txt";

    public static final String nameFileContent = "yangshunxiang";

    @BeforeAll
    public static void initMinIOFileClient() {
        S3FileClientProperties properties = new S3FileClientProperties();
        properties.setEndpoint(ENDPOINT);
        properties.setAccessKey("aFtHTkUZmnlvNqwW");
        properties.setSecretKey("8QQDmxG3gz1NaLbsSKmWE4ihOZlEbGfq");
        FilePathGenerator filepathGenerator = fileMetaData -> UUID.randomUUID().toString();
        s3FileClient = new S3FileClient(properties, filepathGenerator);
    }

    @Test
    @Order(0)
    void getS3FileClientProperties() {
        assertNotNull(s3FileClient.getS3FileClientProperties());
        assertEquals(s3FileClient.getS3FileClientProperties().getEndpoint(), ENDPOINT);
    }



    @Test
    @Order(1)
    void putFile() {
        FileObject fileObject = s3FileClient.putFile(bucket,
                new ByteArrayInputStream(nameFileContent.getBytes()),
                filepathNameTxt,
                FileMetadata.builder().ofFilepath(filepathNameTxt).build());
        assertNotNull(fileObject);
    }

    @Test
    @Order(2)
    void getFile() {
        Optional<FileObject> file = s3FileClient.getFile(bucket, filepathNameTxt);
        assertTrue(file.isPresent());
        assertEquals(file.get().getPart(), bucket);
        assertEquals(file.get().getFilepath(), filepathNameTxt);
    }

    @Test
    void exists() {
    }

    @Test
    void getUrl() {
    }

    @Test
    void deleteFile() {
    }

    @Test
    void getDefaultPart() {
    }
}