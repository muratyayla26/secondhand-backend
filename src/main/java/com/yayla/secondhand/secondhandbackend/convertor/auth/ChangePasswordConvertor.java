package com.yayla.secondhand.secondhandbackend.convertor.auth;

import com.yayla.secondhand.secondhandbackend.model.request.auth.ChangePasswordRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.auth.ChangePasswordVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChangePasswordConvertor {
    ChangePasswordVo convert(ChangePasswordRequest request, Long accountId);
}
