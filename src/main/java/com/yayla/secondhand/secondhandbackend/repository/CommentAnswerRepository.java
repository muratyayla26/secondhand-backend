package com.yayla.secondhand.secondhandbackend.repository;

import com.yayla.secondhand.secondhandbackend.model.entity.CommentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentAnswerRepository extends JpaRepository<CommentAnswer, Long> {
}
