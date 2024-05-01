package com.yayla.secondhand.secondhandbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CommentAnswerDto extends BaseDto {

    private Long commentAnswerId;

    private String content;

    private Long commentId;

    private ProfilePlainDto profile;

    @JsonIgnore
    private Long ownerId;
}
