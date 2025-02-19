package com.newsfeed.domain.posts.entity;

import com.newsfeed.common.entity.BaseEntity;
import com.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.newsfeed.domain.comment.entity.Comment;
import com.newsfeed.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Posts extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Posts(String title, String contents, User userId) {
        this.title = title;
        this.contents = contents;
        this.user = userId;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
