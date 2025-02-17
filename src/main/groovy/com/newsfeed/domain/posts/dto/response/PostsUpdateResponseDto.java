package com.newsfeed.domain.posts.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsUpdateResponseDto {
    private final Long postId;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostsUpdateResponseDto(Long postId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
