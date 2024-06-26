package com.yayla.secondhand.secondhandbackend.convertor.auth;

import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenRefreshConvertor {
    RefreshToken convert(TokenRefreshDto tokenRefreshDto);

    TokenRefreshDto convert(RefreshToken refreshToken);
}
