package com.yayla.secondhand.secondhandbackend.convertor.comment;

import com.yayla.secondhand.secondhandbackend.model.dto.CommentDto;
import com.yayla.secondhand.secondhandbackend.model.entity.Comment;
import com.yayla.secondhand.secondhandbackend.model.request.CommentCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.CommentCreateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentConvertor {

    CommentCreateVo convert(CommentCreateRequest commentCreateRequest, Long ownerId);
    Comment convert(CommentCreateVo commentCreateVo);
    CommentDto convert(Comment comment);
}
