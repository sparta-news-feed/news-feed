package com.newsfeed.domain.posts.entity;

import com.newsfeed.common.entity.BaseEntity;
<<<<<<< HEAD
import com.newsfeed.domain.users.entity.User;
=======
>>>>>>> feat/test
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
<<<<<<< HEAD
@Table(name = "posts")
=======
@Table(name ="posts")
>>>>>>> feat/test
public class Posts extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    private String contents;

<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Posts(String title, String contents, User userId) {
        this.title = title;
        this.contents = contents;
        this.user = userId;
=======
    public Posts(String title, String contents) {
        this.title = title;
        this.contents = contents;
>>>>>>> feat/test
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
