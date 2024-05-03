package com.yayla.secondhand.secondhandbackend.convertor.auth;

import com.yayla.secondhand.secondhandbackend.model.dto.auth.AccountDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.model.vo.auth.RegisterConfirmationVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountConvertor {
    AccountDto convert(Account account);

    RegisterConfirmationVo convert(AccountDto accountDto);
}
