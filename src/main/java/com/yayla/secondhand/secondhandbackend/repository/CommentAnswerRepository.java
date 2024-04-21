package com.yayla.secondhand.secondhandbackend.repository;

import com.yayla.secondhand.secondhandbackend.model.entity.CommentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentAnswerRepository extends JpaRepository<CommentAnswer, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                update CommentAnswer c set
                c.isDeleted=true
                where c.productId = :productId and c.isDeleted=false
            """)
    void removeAllCommentsAnswers(@Param("productId") Long productId);
}
