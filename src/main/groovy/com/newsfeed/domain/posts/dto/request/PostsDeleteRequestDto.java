package com.newsfeed.domain.posts.dto.request;

import lombok.Getter;

@Getter
public class PostsDeleteRequestDto {
    private Long postId;
    private String password;
}
