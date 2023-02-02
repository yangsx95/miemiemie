package com.miemiemie.starter.oss;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云oss自动配置
 *
 * @author yangshunxiang
 * @since 2022/12/16
 */
@Configuration
@EnableConfigurationProperties({OssProperties.class})
@ComponentScan(basePackageClasses = OssAutoConfig.class)
public class OssAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AmazonS3 amazonS3(OssProperties ossProperties) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                ossProperties.getEndpoint(),
                ossProperties.getRegion()
        );
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                ossProperties.getAccessKey(),
                ossProperties.getSecretKey()
        );

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        return AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfiguration)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(ossProperties.getPathStyleAccess())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public OssTemplate ossTemplate(AmazonS3 amazonS3, OssProperties ossProperties) {
        return new OssTemplate(amazonS3, ossProperties);
    }
}
