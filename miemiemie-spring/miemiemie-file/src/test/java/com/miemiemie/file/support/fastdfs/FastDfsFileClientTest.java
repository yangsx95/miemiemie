package com.miemiemie.file.support.fastdfs;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.FilePathGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FastDfsFileClientTest {

    private static FastDfsFileClient fastDfsFileClient;

    private static final String trackerServer = " 10.10.10.6:22122";

    public static final String nameFileContent = "yangshunxiang";

    @BeforeAll
    public static void initFileClient() {
        FastDfsFileClientProperties properties = new FastDfsFileClientProperties();
        properties.setTrackerServers(trackerServer);
        FilePathGenerator filepathGenerator = fileMetaData -> UUID.randomUUID().toString();
        fastDfsFileClient = new FastDfsFileClient(properties, filepathGenerator);
    }

    @Test
    void operate() {
        FileObject fileObject = testPutFile();
        testExists(fileObject);
        testGetUrl(fileObject);
        deleteFile(fileObject);
    }

    private static FileObject testPutFile() {
        FileObject fileObject = fastDfsFileClient.putFile(
                new ByteArrayInputStream(nameFileContent.getBytes(StandardCharsets.UTF_8)),
                FileMetadata.builder().ofFilename("hello.txt").build()
        );
        assertNotNull(fileObject.getFilepath());
        assertNotNull(fileObject.getPart());
        assertNotNull(fileObject.getFileMetadata());
        assertEquals(fileObject.getFileMetadata().get(FileMetadata.FILE_EXTENSION), "txt");
        assertEquals(fileObject.getFileMetadata().get(FileMetadata.FILE_NAME), "hello.txt");

        System.out.println(fileObject.getPart() + "/" + fileObject.getFilepath());

        return fileObject;
    }

    void testExists(FileObject fileObject) {
        boolean exists = fastDfsFileClient.exists(fileObject.getPart(), fileObject.getFilepath());
        assertTrue(exists);
    }

    void testGetUrl(FileObject fileObject) {
        System.out.println(fastDfsFileClient.getUrl(fileObject.getPart(), fileObject.getFilepath()));
    }

    void deleteFile(FileObject fileObject) {
        fastDfsFileClient.deleteFile(fileObject.getPart(), fileObject.getFilepath());
        boolean exists = fastDfsFileClient.exists(fileObject.getPart(), fileObject.getFilepath());
        assertFalse(exists);
    }

}