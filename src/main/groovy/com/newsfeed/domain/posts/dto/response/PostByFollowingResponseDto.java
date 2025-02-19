package com.newsfeed.domain.posts.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostByFollowingResponseDto {
    private final String userName;

    private final String title;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;


    public PostByFollowingResponseDto(String userName, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}


