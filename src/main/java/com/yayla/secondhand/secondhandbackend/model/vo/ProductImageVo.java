package com.yayla.secondhand.secondhandbackend.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class ProductImageVo {
    private MultipartFile file;

    private UUID fileKey;

    private String bucketPath;

    private String originalFileName;
}
