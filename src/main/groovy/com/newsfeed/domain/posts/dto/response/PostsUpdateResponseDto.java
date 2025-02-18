package com.newsfeed.domain.posts.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsUpdateResponseDto {
    private final Long postId;
<<<<<<< HEAD
    private final String username;
=======
>>>>>>> feat/test
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

<<<<<<< HEAD
    public PostsUpdateResponseDto(Long postId, String username, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;
        this.username = username;
=======
    public PostsUpdateResponseDto(Long postId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;
>>>>>>> feat/test
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
