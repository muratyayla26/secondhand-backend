package com.yayla.secondhand.secondhandbackend.controller.comment;

import com.yayla.secondhand.secondhandbackend.manager.comment.CommentAnswerManager;
import com.yayla.secondhand.secondhandbackend.model.request.comment.CommentAnswerCreateRequest;
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

    @DeleteMapping("/{commentAnswerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse deleteCommentAnswer(@PathVariable Long commentAnswerId) {
        return commentAnswerManager.deleteCommentAnswer(commentAnswerId);
    }
}
