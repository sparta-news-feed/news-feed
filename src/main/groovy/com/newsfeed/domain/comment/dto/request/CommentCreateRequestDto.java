package com.newsfeed.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {

//    @NotNull(message = "유효한 id값이 없습니다.")
//    private Long userId;

    @NotNull(message = "유효한 id값이 없습니다.")
    private Long postId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String contents;

}
