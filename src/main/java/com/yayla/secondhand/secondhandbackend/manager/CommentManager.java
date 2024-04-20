package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.comment.CommentConvertor;
import com.yayla.secondhand.secondhandbackend.model.request.CommentCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.CommentCreateVo;
import com.yayla.secondhand.secondhandbackend.service.CommentService;
import com.yayla.secondhand.secondhandbackend.service.SessionInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentManager {

    private final SessionInfoService sessionInfoService;
    private final CommentConvertor commentConvertor;
    private final CommentService commentService;

    public BaseResponse createComment(CommentCreateRequest commentCreateRequest) {
        log.info("Comment creation has started : {}", commentCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        CommentCreateVo commentCreateVo =  commentConvertor.convert(commentCreateRequest, currentAccountId);
        commentService.createComment(commentCreateVo);
        return new BaseResponse();
    }
}
