package com.yayla.secondhand.secondhandbackend.convertor.comment;

import com.yayla.secondhand.secondhandbackend.model.dto.comment.CommentAnswerDto;
import com.yayla.secondhand.secondhandbackend.model.entity.comment.CommentAnswer;
import com.yayla.secondhand.secondhandbackend.model.request.comment.CommentAnswerCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.comment.CommentAnswerCreateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentAnswerConvertor {
    CommentAnswerCreateVo convert(CommentAnswerCreateRequest createRequest, Long ownerId, Long productId);

    CommentAnswer convert(CommentAnswerCreateVo commentAnswerCreateVo);

    CommentAnswerDto convert(CommentAnswer commentAnswer);
}

