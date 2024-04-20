package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.CommentManager;
import com.yayla.secondhand.secondhandbackend.model.request.CommentCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentManager commentManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest) {
        return commentManager.createComment(commentCreateRequest);
    }
}