package com.yayla.secondhand.secondhandbackend.model.vo.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentCreateVo {

    private String content;

    private Long ownerId;

    private Long productId;
}
