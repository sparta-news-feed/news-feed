package com.newsfeed.domain.posts.entity;

import com.newsfeed.common.entity.BaseEntity;
import com.newsfeed.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
