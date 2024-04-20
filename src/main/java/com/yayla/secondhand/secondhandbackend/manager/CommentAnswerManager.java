package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.comment.CommentAnswerConvertor;
import com.yayla.secondhand.secondhandbackend.model.request.CommentAnswerCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.CommentAnswerCreateVo;
import com.yayla.secondhand.secondhandbackend.service.CommentAnswerService;
import com.yayla.secondhand.secondhandbackend.service.SessionInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentAnswerManager {

    private final CommentAnswerService commentAnswerService;
    private final SessionInfoService sessionInfoService;
    private final CommentAnswerConvertor commentAnswerConvertor;

    public BaseResponse createCommentAnswer(CommentAnswerCreateRequest commentAnswerCreateRequest) {
        log.info("Comment answer creation has started : {}", commentAnswerCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        CommentAnswerCreateVo commentCreateVo = commentAnswerConvertor.convert(commentAnswerCreateRequest, currentAccountId);
        commentAnswerService.createCommentAnswer(commentCreateVo);
        return new BaseResponse();
    }

}
