package com.yayla.secondhand.secondhandbackend.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class CommentDto extends BaseDto{

    private Long commentId;

    private String content;

    private Long ownerId;

    private Long productId;

    private List<CommentAnswerDto> commentAnswers = new ArrayList<>();
}
