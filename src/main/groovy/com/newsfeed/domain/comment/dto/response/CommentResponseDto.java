package com.newsfeed.domain.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long commentId;
    private final Long postId;
    private final String username;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public CommentResponseDto(Long commentId, Long postId, String username, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.username = username;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
