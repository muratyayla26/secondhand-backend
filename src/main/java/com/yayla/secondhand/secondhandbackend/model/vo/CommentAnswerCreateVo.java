package com.yayla.secondhand.secondhandbackend.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentAnswerCreateVo {
    private String content;

    private Long commentId;

    private Long ownerId;

    private Long productId;

}
