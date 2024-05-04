package com.yayla.secondhand.secondhandbackend.service.comment;

import com.yayla.secondhand.secondhandbackend.convertor.comment.CommentConvertor;
import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.dto.comment.CommentDto;
import com.yayla.secondhand.secondhandbackend.model.entity.comment.Comment;
import com.yayla.secondhand.secondhandbackend.model.vo.comment.CommentCreateVo;
import com.yayla.secondhand.secondhandbackend.repository.comment.CommentRepository;
import jakarta.transaction.Transactional;
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

    public CommentDto fetchComment(Long commentId) {
        log.info("Comment fetch has started. commentId: {}", commentId);
        Comment comment = commentRepository.findByCommentIdAndIsDeletedIsFalse(commentId).orElseThrow(NotFoundException::new);
        log.info("Comment fetch has ended. commentId: {}", comment.getCommentId());
        return commentConvertor.convert(comment);
    }

    public void deleteComment(Long commentId) {
        log.info("Comment delete has started. commentId: {}", commentId);
        Comment comment = commentRepository.findByCommentIdAndIsDeletedIsFalse(commentId).orElseThrow(NotFoundException::new);
        comment.setDeleted(true);
        commentRepository.save(comment);
        log.info("Comment delete has ended. commentId: {}", commentId);
    }

    @Transactional
    public void deleteProductsComments(Long productId) {
        log.info("Delete products comments has started. productId: {}", productId);
        commentRepository.removeAllCommentsByProductId(productId);
        log.info("Delete products comments has ended. productId: {}", productId);
    }
}
