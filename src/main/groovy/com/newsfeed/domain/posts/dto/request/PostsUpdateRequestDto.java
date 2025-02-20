package com.newsfeed.domain.posts.dto.request;

import lombok.Getter;

@Getter
public class PostsUpdateRequestDto {
    private String password;
    private String title;
    private String contents;
}
