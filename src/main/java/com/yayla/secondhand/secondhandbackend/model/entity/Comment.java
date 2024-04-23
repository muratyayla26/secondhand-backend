package com.yayla.secondhand.secondhandbackend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

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
    @SQLRestriction("is_deleted = false")
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id", insertable = false, updatable = false)
    private List<CommentAnswer> commentAnswers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private ProfilePlain profile;
}
