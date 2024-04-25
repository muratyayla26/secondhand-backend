package com.yayla.secondhand.secondhandbackend.system.utility;

import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class MediaHelper {

    public static final String PROFILE_BUCKET_FOLDER = "profile";
    public static final String PRODUCT_BUCKET_FOLDER = "product";

    private final List<String> allowedImageTypes = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/jpg",
            "image/bmp",
            "image/tiff",
            "image/webp"
    );

    public void validateImageType(MultipartFile file) {
        if(file.isEmpty()) {
            throw new BusinessException("File is empty");
        }

        if(!allowedImageTypes.contains(file.getContentType())) {
            throw new BusinessException("Unsupported image type");
        }
    }

    public UUID generateImageKey(){
        return UUID.randomUUID();
    }

    public String generateBucketPath(String bucketPath, UUID fileKey) {
        return String.format("%s/%s", bucketPath, fileKey);
    }

}