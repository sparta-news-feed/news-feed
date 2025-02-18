package com.newsfeed.domain.comment.controller;

import com.newsfeed.common.Const;
import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.comment.dto.request.CommentCreateRequestDto;
import com.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.newsfeed.domain.comment.service.CommentService;
import com.newsfeed.domain.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Const.ROOT_API_PATH + "/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostsService postsService;

    //댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody CommentCreateRequestDto requestDto
    ) {
        Long userId = JwtUtil.extractUserId(authorization); // 유저아이디 가져옴 :JWT 토큰에서 userId 추출
        CommentResponseDto responseDto = commentService.createComment(userId, requestDto.getPostId(), requestDto.getContents());

        return ResponseEntity.ok(responseDto);
    }
}
