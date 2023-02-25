package com.miemiemie.starter.file.support.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.miemiemie.starter.file.FileMetadata;
import com.miemiemie.starter.file.FileObject;
import com.miemiemie.starter.file.exception.FileClientException;
import com.miemiemie.starter.file.support.AbstractFileClient;
import com.miemiemie.starter.file.support.FilePathGenerator;
import lombok.Getter;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * 基于S3协议的文件客户端
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public class S3FileClient extends AbstractFileClient {

    @Getter
    private final S3FileClientProperties s3FileClientProperties;

    @Getter
    private final AmazonS3 amazonS3;

    public S3FileClient(FilePathGenerator filePathGenerator, FileMetadata defaultFileMetadata, S3FileClientProperties s3FileClientProperties) {
        super(filePathGenerator, defaultFileMetadata);
        this.s3FileClientProperties = s3FileClientProperties;
        amazonS3 = initAmazonS3(s3FileClientProperties);
    }

    public AmazonS3 initAmazonS3(S3FileClientProperties s3FileClientProperties) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                s3FileClientProperties.getEndpoint(),
                s3FileClientProperties.getRegion()
        );
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                s3FileClientProperties.getAccessKey(),
                s3FileClientProperties.getSecretKey()
        );

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        return AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfiguration)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(s3FileClientProperties.isPathStyleAccess())
                .build();
    }

    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }


    @Override
    public FileObject putFile(String part, InputStream content, String filepath, FileMetadata fileMetaData) throws FileClientException {
        createBucket(part);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(fileMetaData.getContentType());
        PutObjectRequest putObjectRequest = new PutObjectRequest(part, filepath, content, objectMetadata);
        amazonS3.putObject(putObjectRequest);
        return getFile(part, filepath).orElseThrow(() -> new FileClientException("file put error, can not get target file"));
    }

    @Override
    public Optional<FileObject> getFile(String part, String filepath) {
        S3Object object = amazonS3.getObject(part, filepath);
        return Optional.of(new S3FileObject(object));
    }

    @Override
    public boolean exists(String part, String filepath) {
        return amazonS3.doesObjectExist(part, filepath);
    }

    @Override
    public URI getUrl(String part, String filepath) {
        try {
            return amazonS3.getUrl(part, filepath).toURI();
        } catch (URISyntaxException e) {
            throw new FileClientException("get file url failed", e);
        }
    }

    @Override
    public void deleteFile(String part, String filepath) throws FileClientException {
        amazonS3.deleteObject(part, filepath);
    }

    @Override
    protected String getDefaultPart() {
        return s3FileClientProperties.getDefaultBucketName();
    }
}
