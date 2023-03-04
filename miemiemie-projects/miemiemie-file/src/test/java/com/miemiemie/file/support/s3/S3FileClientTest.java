package com.miemiemie.file.support.s3;

import cn.hutool.core.io.IoUtil;
import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.FilePathGenerator;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
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
        properties.setAccessKey("2tsJuuafAUtjMrWv");
        properties.setSecretKey("UKrpr2Y5AIZQnpnqNJx13RTW60H9klAa");
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
    @Order(0)
    void getDefaultPart() {
        assertEquals(s3FileClient.getDefaultPart(), bucket);
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
        FileObject fileObject = file.get();
        assertEquals(fileObject.getPart(), bucket);
        assertEquals(fileObject.getFilepath(), filepathNameTxt);
        assertEquals(IoUtil.read(fileObject.getContent().get(), StandardCharsets.UTF_8), nameFileContent);
    }

    @Test
    @Order(2)
    void getNotExistsFile() {
        Optional<FileObject> file = s3FileClient.getFile(bucket, "notexist/filename.ext");
        assertFalse(file.isPresent());
    }

    @Test
    @Order(3)
    void getUrl() {
        URI url = s3FileClient.getUrl(bucket, filepathNameTxt);
        System.out.println(url);
        assertNotNull(url);
    }

    @Test
    void deleteFile() {
        s3FileClient.deleteFile(bucket, filepathNameTxt);
        assertFalse(s3FileClient.exists(bucket, filepathNameTxt));
    }
}