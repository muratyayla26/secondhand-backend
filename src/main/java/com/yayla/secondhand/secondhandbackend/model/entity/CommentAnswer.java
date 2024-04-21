package com.yayla.secondhand.secondhandbackend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_answer")
public class CommentAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_answer_id")
    private Long commentAnswerId;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "product_id")
    private Long productId;
//    @ManyToOne
//    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
//    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private ProfilePlain profile;
}
