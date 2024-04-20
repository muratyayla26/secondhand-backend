package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.CommentAnswerManager;
import com.yayla.secondhand.secondhandbackend.model.request.CommentAnswerCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/comment-answer")
@RequiredArgsConstructor
public class CommentAnswerController {

    private final CommentAnswerManager commentAnswerManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createCommentAnswer(@Valid @RequestBody CommentAnswerCreateRequest commentAnswerCreateRequest) {
        return commentAnswerManager.createCommentAnswer(commentAnswerCreateRequest);
    }
}
