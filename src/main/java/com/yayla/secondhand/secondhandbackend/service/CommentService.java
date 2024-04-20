package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.convertor.comment.CommentConvertor;
import com.yayla.secondhand.secondhandbackend.model.entity.Comment;
import com.yayla.secondhand.secondhandbackend.model.vo.CommentCreateVo;
import com.yayla.secondhand.secondhandbackend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentConvertor commentConvertor;

    public void createComment(CommentCreateVo commentCreateVo) {
        log.info("Comment creation has started. commentCreateVo: {}", commentCreateVo.toString());
        Comment comment = commentConvertor.convert(commentCreateVo);
        Comment saved = commentRepository.save(comment);
        log.info("Comment creation has ended. saved.getCommentId: {}", saved.getCommentId());
    }
}
