package com.yayla.secondhand.secondhandbackend.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ProfileImageVo {

    private Long accountId;

    private MultipartFile file;

    private UUID fileKey;

    private String bucketPath;
}
