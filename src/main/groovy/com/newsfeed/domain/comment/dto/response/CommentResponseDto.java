package com.newsfeed.domain.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long commentId;
    private final Long userId;
    private final Long postId;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Long commentId, Long userId, Long postId, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
