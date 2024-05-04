package com.yayla.secondhand.secondhandbackend.manager.comment;

import com.yayla.secondhand.secondhandbackend.convertor.comment.CommentConvertor;
import com.yayla.secondhand.secondhandbackend.model.dto.comment.CommentDto;
import com.yayla.secondhand.secondhandbackend.model.request.comment.CommentCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.comment.CommentCreateVo;
import com.yayla.secondhand.secondhandbackend.service.comment.CommentAnswerService;
import com.yayla.secondhand.secondhandbackend.service.comment.CommentService;
import com.yayla.secondhand.secondhandbackend.service.SessionInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentManager {

    private final SessionInfoService sessionInfoService;
    private final CommentConvertor commentConvertor;
    private final CommentService commentService;
    private final CommentAnswerService commentAnswerService;

    public BaseResponse createComment(CommentCreateRequest commentCreateRequest) {
        log.info("Comment creation has started : {}", commentCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        CommentCreateVo commentCreateVo = commentConvertor.convert(commentCreateRequest, currentAccountId);
        commentService.createComment(commentCreateVo);
        return new BaseResponse();
    }

    @Transactional
    public BaseResponse deleteComment(Long commentId) {
        log.info("Comment delete has started. commentId: {}", commentId);
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(commentId, currentAccountId);
        commentAnswerService.deleteCommentsCommentsAnswers(commentId);
        commentService.deleteComment(commentId);
        return new BaseResponse();
    }

    private void validateAccess(Long commentId, Long currentAccountId) {
        CommentDto commentDto = commentService.fetchComment(commentId);
        if (!commentDto.getOwnerId().equals(currentAccountId)) {
            throw new AccessDeniedException("You do not have access to this comment");
        }
    }
}
