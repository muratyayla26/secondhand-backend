package com.yayla.secondhand.secondhandbackend.convertor.comment;

import com.yayla.secondhand.secondhandbackend.model.entity.CommentAnswer;
import com.yayla.secondhand.secondhandbackend.model.request.CommentAnswerCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.CommentAnswerCreateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentAnswerConvertor {
    CommentAnswerCreateVo convert(CommentAnswerCreateRequest createRequest, Long ownerId);
    CommentAnswer convert(CommentAnswerCreateVo commentAnswerCreateVo);
}

