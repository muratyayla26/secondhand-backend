package com.yayla.secondhand.secondhandbackend.convertor.comment;

import com.yayla.secondhand.secondhandbackend.model.dto.comment.CommentDto;
import com.yayla.secondhand.secondhandbackend.model.entity.comment.Comment;
import com.yayla.secondhand.secondhandbackend.model.request.comment.CommentCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.comment.CommentCreateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentConvertor {

    CommentCreateVo convert(CommentCreateRequest commentCreateRequest, Long ownerId);

    Comment convert(CommentCreateVo commentCreateVo);

    CommentDto convert(Comment comment);
}
