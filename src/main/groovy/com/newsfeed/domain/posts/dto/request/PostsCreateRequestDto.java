package com.newsfeed.domain.posts.dto.request;

import lombok.Getter;

@Getter
public class PostsCreateRequestDto {

    private String title;
    private String contents;
}
