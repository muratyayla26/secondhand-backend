package com.yayla.secondhand.secondhandbackend.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class S3Properties {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    @Value("${aws.s3.endpoint}")
    private String bucketEndpoint;

    @Value("${aws.s3.region}")
    private String bucketRegion;

    @Value("${aws.s3.bucket}")
    private String bucketName;
}
