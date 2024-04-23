package com.yayla.secondhand.secondhandbackend.repository;

import com.yayla.secondhand.secondhandbackend.model.entity.CommentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentAnswerRepository extends JpaRepository<CommentAnswer, Long> {

    Optional<CommentAnswer> findByCommentAnswerIdAndIsDeletedIsFalse(Long commentAnswerId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                update CommentAnswer c set
                c.isDeleted=true
                where c.productId = :productId and c.isDeleted=false
            """)
    void removeAllCommentsAnswersByProductId(@Param("productId") Long productId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                update CommentAnswer c set
                c.isDeleted=true
                where c.commentId = :commentId and c.isDeleted=false
            """)
    void removeAllCommentsAnswersByCommentId(@Param("commentId") Long commentId);
}
