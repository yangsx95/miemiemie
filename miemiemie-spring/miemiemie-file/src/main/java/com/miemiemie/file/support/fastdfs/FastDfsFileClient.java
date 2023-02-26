package com.miemiemie.file.support.fastdfs;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import com.miemiemie.file.FilePathGenerator;
import com.miemiemie.file.exception.FileClientException;
import com.miemiemie.file.pool.AbstractPooledFileClient;
import com.miemiemie.file.pool.FileClientPoolProperties;
import com.miemiemie.file.util.Util;
import org.apache.commons.pool2.PooledObjectFactory;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

/**
 * @author yangshunxiang
 * @since 2023/2/25
 */
public class FastDfsFileClient extends AbstractPooledFileClient<TrackerServer> {

    public static String NULL_GROUP = "";

    private final FastDfsFileClientProperties fastDfsFileClientProperties;

    public FastDfsFileClient(FastDfsFileClientProperties fastDfsFileClientProperties,
                             FilePathGenerator filePathGenerator) {
        this(fastDfsFileClientProperties, filePathGenerator, new TrackerServerFactory(fastDfsFileClientProperties));
    }

    public FastDfsFileClient(FastDfsFileClientProperties fastDfsFileClientProperties,
                             FilePathGenerator filePathGenerator,
                             PooledObjectFactory<TrackerServer> factory) {
        super(filePathGenerator, factory);
        this.fastDfsFileClientProperties = fastDfsFileClientProperties;
    }

    @Override
    protected FileClientPoolProperties getPoolProperties() {
        return fastDfsFileClientProperties.getPool();
    }

    @Override
    protected FileObject doPutFile(TrackerServer client, String part, InputStream content, String filepath, FileMetadata fileMetaData) throws Exception {
        StorageClient1 storageClient1 = new StorageClient1(client, null);
        String fileExt = fileMetaData.get(FileMetadata.FILE_EXTENSION);
        if (!StringUtils.hasText(fileExt)) {
            fileExt = Util.getFileExtension(filepath);
        }
        Object[] array = fileMetaData.entrySet().stream().map(data -> {
            NameValuePair pair = new NameValuePair();
            pair.setName(data.getKey());
            pair.setValue(data.getValue());
            return pair;
        }).toArray();
        if (NULL_GROUP.equals(part)) {
            part = null;
        }
        String groupAndFilePath = storageClient1.upload_file1(part, fileExt, (NameValuePair[]) array);
//        return new FastDfsFileObject(groupAndFilePath, fileMetaData);
        return null;
    }

    @Override
    public Optional<FileObject> getFile(String filepath) {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected Optional<FileObject> doGetFile(TrackerServer client, String part, String filepath) throws Exception {
        return Optional.empty();
    }

    @Override
    public boolean exists(String filepath) {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected boolean doExists(TrackerServer client, String part, String filepath) throws Exception {
        return false;
    }

    @Override
    public URI getUrl(String filepath) {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected URI doGetUrl(TrackerServer client, String part, String filepath) throws Exception {
        return null;
    }

    @Override
    public void deleteFile(String filepath) throws FileClientException {
        throw new FileClientException("must specify FastDFS group");
    }

    @Override
    protected Void doDelete(TrackerServer client, String part, String filepath) throws Exception {
        return null;
    }

    @Override
    protected String getDefaultPart() {
        return NULL_GROUP;
    }
}
