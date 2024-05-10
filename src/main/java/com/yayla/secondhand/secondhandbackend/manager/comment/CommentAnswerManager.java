package com.yayla.secondhand.secondhandbackend.manager.comment;

import com.yayla.secondhand.secondhandbackend.convertor.comment.CommentAnswerConvertor;
import com.yayla.secondhand.secondhandbackend.model.dto.comment.CommentAnswerDto;
import com.yayla.secondhand.secondhandbackend.model.dto.comment.CommentDto;
import com.yayla.secondhand.secondhandbackend.model.request.comment.CommentAnswerCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.comment.CommentAnswerCreateVo;
import com.yayla.secondhand.secondhandbackend.service.comment.CommentAnswerService;
import com.yayla.secondhand.secondhandbackend.service.comment.CommentService;
import com.yayla.secondhand.secondhandbackend.service.SessionInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentAnswerManager {

    private final CommentAnswerService commentAnswerService;
    private final SessionInfoService sessionInfoService;
    private final CommentAnswerConvertor commentAnswerConvertor;
    private final CommentService commentService;

    public BaseResponse createCommentAnswer(CommentAnswerCreateRequest commentAnswerCreateRequest) {
        log.info("Comment answer creation has started : {}", commentAnswerCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        sessionInfoService.validateProfileExists(currentAccountId);
        CommentDto commentDto = commentService.fetchComment(commentAnswerCreateRequest.getCommentId());
        CommentAnswerCreateVo commentCreateVo = commentAnswerConvertor.convert(commentAnswerCreateRequest, currentAccountId, commentDto.getProductId());
        commentAnswerService.createCommentAnswer(commentCreateVo);
        return new BaseResponse();
    }

    public BaseResponse deleteCommentAnswer(Long commentAnswerId) {
        log.info("Comment answer deletion has started : {}", commentAnswerId);
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(commentAnswerId, currentAccountId);
        commentAnswerService.deleteCommentAnswer(commentAnswerId);
        return new BaseResponse();
    }

    private void validateAccess(Long commentAnswerId, Long currentAccountId) {
        CommentAnswerDto commentAnswerDto = commentAnswerService.fetchCommentAnswer(commentAnswerId);
        if (!commentAnswerDto.getOwnerId().equals(currentAccountId)) {
            throw new AccessDeniedException("You do not have access to this comment answer");
        }
    }

}
