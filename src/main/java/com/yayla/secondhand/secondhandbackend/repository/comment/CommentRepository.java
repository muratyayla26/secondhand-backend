package com.yayla.secondhand.secondhandbackend.repository.comment;

import com.yayla.secondhand.secondhandbackend.model.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdAndIsDeletedIsFalse(Long commentId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            update Comment c
            set c.isDeleted=true
            where c.productId=:productId and c.isDeleted=false
            """)
    void removeAllCommentsByProductId(@Param("productId") Long productId);
}
