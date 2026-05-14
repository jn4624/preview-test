package com.jyujyu.inflearnspringtest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.util.List;

@Configuration
public class S3Config {

    /**
     * 아래 두 Bean은 직접 설정하지 않으면 기본적으로 AWS SDK에서 제공해줄 수 있다
     */

    @Value("${aws.endpoint}")
    private String awsEndpoint;

    /**
     * AWS SDK에서 AWS 서비스를 사용하기 위해 별도의 인증 과정을 거쳐야 하는데
     * 그 인증 정보를 담아 제공해주는 역할의 클래스
     */
    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
                .reuseLastProviderEnabled(true)
                .credentialsProviders(
                        List.of(
                                DefaultCredentialsProvider.create(),
                                StaticCredentialsProvider.create(AwsBasicCredentials.create("foo", "bar"))
                        )
                ).build();
    }

    /**
     * AWS 서비스를 실제로 사용하기 위한 역할의 클라이언트 클래스
     * 실제로 내부적으로는 API를 호출하고 있다
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(Region.AP_NORTHEAST_2)
                .endpointOverride(URI.create(awsEndpoint))
                .build();
    }
}
