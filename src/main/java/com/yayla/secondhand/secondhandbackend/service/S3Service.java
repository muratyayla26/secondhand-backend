package com.yayla.secondhand.secondhandbackend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public void uploadFile(MultipartFile file, String keyName) throws IOException {
        PutObjectResult result = s3Client.putObject(bucketName, keyName, file.getInputStream(), null);
        log.info("Upload file successfully : {}", result.getMetadata());
    }

    public S3Object getFile(String keyName) {
        return s3Client.getObject(bucketName, keyName);
    }
}
