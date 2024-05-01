package com.yayla.secondhand.secondhandbackend.convertor.profile;

import com.yayla.secondhand.secondhandbackend.model.dto.ProfileDto;
import com.yayla.secondhand.secondhandbackend.model.entity.Profile;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileImageVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileUpdateVo;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProfileConvertor {
    ProfileCreateVo convert(ProfileCreateRequest profileCreateRequest, Long accountId);

    ProfileDto convert(Profile profile);

    Profile convert(ProfileCreateVo profileCreateVo);

    ProfileUpdateVo convert(ProfileUpdateRequest profileUpdateRequest, Long accountId);

    ProfileImageVo convert(Long accountId, MultipartFile file, UUID fileKey, String bucketPath);
}
