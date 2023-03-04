package com.miemiemie.file.support.fastdfs;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.FilePathGenerator;
import com.miemiemie.file.exception.FileClientException;
import com.miemiemie.file.pool.AbstractPooledFileClient;
import com.miemiemie.file.util.Util;
import lombok.Getter;
import org.apache.commons.pool2.PooledObjectFactory;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author yangshunxiang
 * @since 2023/2/25
 */
public class FastDfsFileClient extends AbstractPooledFileClient<TrackerServer> {

    public static String NULL_GROUP = "";

    @Getter
    private final FastDfsFileClientProperties fastDfsFileClientProperties;

    public FastDfsFileClient(FastDfsFileClientProperties fastDfsFileClientProperties,
                             FilePathGenerator filePathGenerator) {
        this(fastDfsFileClientProperties, filePathGenerator, new TrackerServerFactory(fastDfsFileClientProperties));
    }

    public FastDfsFileClient(FastDfsFileClientProperties fastDfsFileClientProperties,
                             FilePathGenerator filePathGenerator,
                             PooledObjectFactory<TrackerServer> factory) {
        super(filePathGenerator, fastDfsFileClientProperties.getPool(), factory);
        this.fastDfsFileClientProperties = fastDfsFileClientProperties;
    }

    @Override
    protected FileObject doPutFile(TrackerServer client, String part, InputStream content, String filepath, FileMetadata fileMetaData) throws Exception {
        StorageClient storageClient = new StorageClient(client, null);
        String fileExt = fileMetaData.get(FileMetadata.FILE_EXTENSION);
        if (!StringUtils.hasText(fileExt)) {
            fileExt = Util.getFileExtension(filepath);
        }

        NameValuePair[] nameValuePairs = generateNameValuePair(fileMetaData);

        if (NULL_GROUP.equals(part)) {
            part = null;
        }
        byte[] bytes = getContentBytes(content);
        String[] uploadResult = storageClient.upload_file(part, bytes, fileExt, nameValuePairs);
        if (Objects.isNull(uploadResult) || uploadResult.length != 2) {
            throw new FileClientException("upload file error, result path is blank");
        }

        NameValuePair[] metadata = storageClient.get_metadata(uploadResult[0], uploadResult[1]);
        FileMetadata fileMetadata = generateFileMetadata(metadata);
        return FileObject.builder()
                .part(uploadResult[0])
                .filepath(uploadResult[1])
                .metadata(fileMetadata)
                .contentSupplier(getContentInputStreamSupplier(uploadResult[0], uploadResult[1]))
                .build();
    }

    private Supplier<InputStream> getContentInputStreamSupplier(String group, String filepath) {
        return () -> getPoolTemplate().getClientAndOpr(trackerServer -> {
            StorageClient storageClient = new StorageClient(trackerServer, null);
            byte[] bytes = storageClient.download_file(group, filepath);
            return new ByteArrayInputStream(bytes);
        }, "get file content error");
    }

    private NameValuePair[] generateNameValuePair(FileMetadata fileMetaData) {
        NameValuePair[] nameValuePairs = new NameValuePair[fileMetaData.size()];
        fileMetaData.entrySet().stream()
                .map(data -> {
                    NameValuePair pair = new NameValuePair();
                    pair.setName(data.getKey());
                    pair.setValue(data.getValue());
                    return pair;
                })
                .collect(Collectors.toList())
                .toArray(nameValuePairs);
        return nameValuePairs;
    }

    private FileMetadata generateFileMetadata(@Nullable NameValuePair[] nameValuePairs) {
        FileMetadata fileMetadata = FileMetadata.builder().build();
        if (Objects.isNull(nameValuePairs)) {
            return fileMetadata;
        }

        for (NameValuePair nameValuePair : nameValuePairs) {
            fileMetadata.put(nameValuePair.getName(), nameValuePair.getValue());
        }
        return fileMetadata;
    }

    private static byte[] getContentBytes(InputStream content) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len;
        while ((len = content.read(buff)) != -1) {
            swapStream.write(buff, 0, len);
        }
        return swapStream.toByteArray();
    }

    @Override
    public Optional<FileObject> getFile(String filepath) {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected Optional<FileObject> doGetFile(TrackerServer client, String part, String filepath) throws Exception {
        StorageClient storageClient = new StorageClient(client, null);
        if (!doExists(client, part, filepath)) {
            return Optional.empty();
        }

        NameValuePair[] metadata = storageClient.get_metadata(part, filepath);
        FileMetadata fileMetadata = generateFileMetadata(metadata);
        return Optional.of(FileObject.builder()
                .part(part)
                .filepath(filepath)
                .metadata(fileMetadata)
                .contentSupplier(getContentInputStreamSupplier(part, filepath))
                .build());
    }

    @Override
    public boolean exists(String filepath) {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected boolean doExists(TrackerServer client, String part, String filepath) throws Exception {
        StorageClient storageClient = new StorageClient(client, null);
        return exists1(part, filepath, storageClient);
    }

    private static boolean exists1(String part, String filepath, StorageClient storageClient) throws Exception {
        FileInfo fileInfo = storageClient.query_file_info(part, filepath);
        return Objects.nonNull(fileInfo);
    }

    @Override
    public URI getUrl(String filepath) {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected URI doGetUrl(TrackerServer client, String part, String filepath) throws Exception {
        String dataAccessHost = fastDfsFileClientProperties.getDataAccessHost();
        if (dataAccessHost.endsWith("/")) {
            return new URI(dataAccessHost + part + "/" + filepath);
        }
        return new URI(dataAccessHost + "/" + part + "/" + filepath);
    }

    @Override
    public void deleteFile(String filepath) throws FileClientException {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected Void doDelete(TrackerServer client, String part, String filepath) throws Exception {
        StorageClient storageClient = new StorageClient(client, null);
        if (exists1(part, filepath, storageClient)) {
            storageClient.delete_file(part, filepath);
        }
        return null;
    }

    @Override
    protected String getDefaultPart() {
        return NULL_GROUP;
    }
}
