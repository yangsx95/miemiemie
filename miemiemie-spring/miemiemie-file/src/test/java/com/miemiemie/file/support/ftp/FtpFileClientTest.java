package com.miemiemie.file.support.ftp;

import cn.hutool.core.io.IoUtil;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FtpFileClientTest {

    private static FtpFileClient ftpFileClient;

    private static final String rootDir = "data";

    private static final String baseDir = Paths.get(rootDir, "attachment").toString();

    private static final String filepathNameTxt = "a/name.txt";

    public static final String nameFileContent = "yangshunxiang";

    @BeforeAll
    public static void initFileClient() {
        FtpFileClientProperties properties = new FtpFileClientProperties();
        properties.setBaseDir(baseDir);
        properties.setHost("127.0.0.1");
        properties.setPort(21);
        properties.setUsername("ftpuser");
        properties.setPassword("ftppass");
        properties.setPassiveMode(true);
        FilePathGenerator filepathGenerator = fileMetaData -> UUID.randomUUID().toString();
        ftpFileClient = new FtpFileClient(properties, filepathGenerator);
    }
    @Test
    void getPoolProperties() {
        assertNotNull(ftpFileClient.getProperties());
        assertEquals(ftpFileClient.getProperties().getBaseDir(), baseDir);
    }

    @Test
    @Order(1)
    void putFile() {
        FileObject fileObject = ftpFileClient.putFile(baseDir,
                new ByteArrayInputStream(nameFileContent.getBytes(StandardCharsets.UTF_8)),
                filepathNameTxt,
                FileMetadata.builder()
                        .ofFilename(filepathNameTxt)
                        .build()
        );
        assertEquals(fileObject.getPart(), baseDir);
        assertEquals(fileObject.getFilepath(), filepathNameTxt);
        assertEquals(IoUtil.read(fileObject.getContent().get(), StandardCharsets.UTF_8), nameFileContent);
    }

    @Test
    @Order(2)
    void getFile() {
        Optional<FileObject> file = ftpFileClient.getFile(baseDir, filepathNameTxt);
        assertTrue(file.isPresent());
        FileObject fileObject = file.get();
        assertEquals(fileObject.getPart(), baseDir);
        assertEquals(fileObject.getFilepath(), filepathNameTxt);
        assertEquals(IoUtil.read(fileObject.getContent().get(), StandardCharsets.UTF_8), nameFileContent);
    }

    @Test
    @Order(2)
    void getNotExistsFile() {
        Optional<FileObject> file = ftpFileClient.getFile(baseDir, "notexist/filename.ext");
        assertFalse(file.isPresent());
    }

    @Test
    @Order(3)
    void getUrl() {
        URI url = ftpFileClient.getUrl(baseDir, filepathNameTxt);
        System.out.println(url);
        assertNotNull(url);
    }

    @Test
    @Order(4)
    void deleteFile() {
        ftpFileClient.deleteFile(baseDir, filepathNameTxt);
        assertFalse(ftpFileClient.exists(baseDir, filepathNameTxt));
    }

}