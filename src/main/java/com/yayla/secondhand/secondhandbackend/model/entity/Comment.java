package com.yayla.secondhand.secondhandbackend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "product_id")
    private Long productId;

//    @ManyToOne
//    @JoinColumn(name = "product_id", insertable = false, updatable = false)
//    private Product product;

    @OneToMany
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private List<CommentAnswer> commentAnswers = new ArrayList<>();

}
