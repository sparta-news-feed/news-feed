package com.newsfeed.domain.comment.entity;

import com.newsfeed.common.entity.BaseEntity;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //댓글 Id 생성
    private Long commentId;

    @Column(nullable = false, length = 200) // 댓글 Id
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 유저 테이블과 조인
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id", nullable = false ) //뉴스 테이블과 조인
    private Posts posts;

    public Comment(String contents) {
        this.contents = contents;
    }

    public void setUser(User user) { // 댓글-사용자 연관관계 편의 메서드 : setUser 이름 임의로 지정
        this.user = user;
    } // 연관관계 편의 메서드

    public void setPosts(Posts posts) { // 댓글-게시글 연관관계 편의 메서드 : setPosts 이름 임의로 지정
        this.posts = posts;
    } // 연관관계 편의 메서드

    public Comment(User user, Posts posts, String contents) { // 필드 생성자
        this.user = user;
        this.posts = posts;
        this.contents = contents;
    }


}
