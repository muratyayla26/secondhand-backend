package com.yayla.secondhand.secondhandbackend.model.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayla.secondhand.secondhandbackend.model.dto.BaseDto;
import com.yayla.secondhand.secondhandbackend.model.dto.profile.ProfilePlainDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class CommentDto extends BaseDto {

    private Long commentId;

    private String content;

    private Long productId;

    private List<CommentAnswerDto> commentAnswers = new ArrayList<>();

    private ProfilePlainDto profile;

    @JsonIgnore
    private Long ownerId;
}
