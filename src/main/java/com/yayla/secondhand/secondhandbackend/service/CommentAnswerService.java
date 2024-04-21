package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.convertor.comment.CommentAnswerConvertor;
import com.yayla.secondhand.secondhandbackend.model.entity.CommentAnswer;
import com.yayla.secondhand.secondhandbackend.model.vo.CommentAnswerCreateVo;
import com.yayla.secondhand.secondhandbackend.repository.CommentAnswerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentAnswerService {

    private final CommentAnswerRepository commentAnswerRepository;
    private final CommentAnswerConvertor commentAnswerConvertor;

    public void createCommentAnswer(CommentAnswerCreateVo commentAnswerCreateVo){
        log.info("Comment answer creation has started. commentCreateVo: {}", commentAnswerCreateVo.toString());
        CommentAnswer commentAnswer = commentAnswerConvertor.convert(commentAnswerCreateVo);
        CommentAnswer saved = commentAnswerRepository.save(commentAnswer);
        log.info("Comment answer has been saved. commentAnswer: {}", saved.getCommentAnswerId());
    }

    @Transactional
    public void deleteProductsCommentsAnswers(Long productId){
        log.info("Delete products comments answers has started. productId: {}", productId);
        commentAnswerRepository.removeAllCommentsAnswers(productId);
        log.info("Delete products comments answers has ended. productId: {}", productId);
    }
}
