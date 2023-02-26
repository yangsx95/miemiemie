package com.miemiemie.file.support.local;

import cn.hutool.core.io.FileUtil;
import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.FilePathGenerator;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yangshunxiang
 * @since 2023/2/26
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LocalFileClientTest {

    private static LocalFileClient localFileClient;

    private static final String rootDir = "data";

    private static final String baseDir = Paths.get(rootDir, "attachment").toString();

    private static final String filepathNameTxt = "a/name.txt";

    @BeforeAll
    public static void initFileClient() {
        LocalFileClientProperties properties = new LocalFileClientProperties();
        properties.setBaseDir(baseDir);
        FilePathGenerator filepathGenerator = fileMetaData -> UUID.randomUUID().toString();
        localFileClient = new LocalFileClient(properties, filepathGenerator);
    }

    @Test
    @Order(0)
    void clientDefaultPart() {
        LocalFileClient client = new LocalFileClient(metadata -> UUID.randomUUID().toString());
        assertEquals(client.getDefaultPart(), LocalFileClientProperties.DEFAULT.getBaseDir());
    }

    @Test
    @Order(0)
    void getLocalFileClientProperties() {
        assertEquals(localFileClient.getLocalFileClientProperties().getBaseDir(), baseDir);
    }

    @Test
    @Order(0)
    void getDefaultPart() {
        assertEquals(localFileClient.getDefaultPart(), baseDir);
    }

    @Test
    @Order(1)
    void putFile() {
        FileObject fileObject = localFileClient.putFile(baseDir,
                new ByteArrayInputStream("yangshunxiang".getBytes(StandardCharsets.UTF_8)),
                filepathNameTxt,
                FileMetadata.builder()
                        .ofFilename(filepathNameTxt)
                        .build()
        );
        assertEquals(fileObject.getPart(), baseDir);
        assertEquals(fileObject.getFilepath(), filepathNameTxt);
    }

    @Test
    @Order(2)
    void putFileWithoutFilepath() {
        FileObject fileObject = localFileClient.putFile(baseDir,
                new ByteArrayInputStream("18".getBytes(StandardCharsets.UTF_8)),
                FileMetadata.builder()
                        .ofFileExtension("txt")
                        .build());
        assertEquals(fileObject.getPart(), baseDir);
        System.out.println(fileObject.getFilepath());
    }

    @Test
    @Order(2)
    void getFile() {
        Optional<FileObject> file = localFileClient.getFile(baseDir, filepathNameTxt);
        assertTrue(file.isPresent());
        FileObject fileObject = file.get();
        assertEquals(fileObject.getPart(), baseDir);
        assertEquals(fileObject.getFilepath(), filepathNameTxt);
    }

    @Test
    @Order(2)
    void getNotExistFile() {
        Optional<FileObject> file = localFileClient.getFile(baseDir, "notexist/filename.ext");
        assertFalse(file.isPresent());
    }

    @Test
    @Order(3)
    void getUrl() {
        URI url = localFileClient.getUrl(baseDir, filepathNameTxt);
        System.out.println(url);
        assertNotNull(url);
    }

    @Test
    @Order(4)
    void deleteFile() {
        localFileClient.deleteFile(baseDir, filepathNameTxt);
        assertFalse(localFileClient.exists(baseDir, filepathNameTxt));
    }

    @AfterAll
    static void deleteDir() {
        FileUtil.del(Paths.get(rootDir));
    }
}