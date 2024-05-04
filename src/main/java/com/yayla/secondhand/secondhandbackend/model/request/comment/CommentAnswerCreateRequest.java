package com.yayla.secondhand.secondhandbackend.model.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentAnswerCreateRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long commentId;
}
