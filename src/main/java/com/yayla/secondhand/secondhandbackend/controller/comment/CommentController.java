package com.yayla.secondhand.secondhandbackend.controller.comment;

import com.yayla.secondhand.secondhandbackend.manager.comment.CommentManager;
import com.yayla.secondhand.secondhandbackend.model.request.comment.CommentCreateRequest;
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

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse deleteComment(@PathVariable Long commentId) {
        return commentManager.deleteComment(commentId);
    }
}
