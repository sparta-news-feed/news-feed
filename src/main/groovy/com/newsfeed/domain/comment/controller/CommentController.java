package com.newsfeed.domain.comment.controller;

import com.newsfeed.common.Const;
import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.comment.dto.request.CommentCreateRequestDto;
import com.newsfeed.domain.comment.dto.request.CommentUpdateRequestDto;
import com.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.newsfeed.domain.comment.dto.response.CommentUpdateResponseDto;
import com.newsfeed.domain.comment.service.CommentService;
import com.newsfeed.domain.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Const.ROOT_API_PATH + "/comments")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody CommentCreateRequestDto requestDto
    ) {
        Long userId = JwtUtil.extractUserId(authorization); // 유저아이디 가져옴 :JWT 토큰에서 userId 추출

        return ResponseEntity.ok(commentService.createComment(userId, requestDto));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentResponseDto>> findPostComment(
            @PathVariable("postId") Long postId
    ) {
        List<CommentResponseDto> responseDtos = commentService.findPostComment(postId);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<CommentResponseDto>> findUserComment(
            @PathVariable("id") Long id
    ) {
        List<CommentResponseDto> dtos = commentService.findUserComment(id);
        return ResponseEntity.ok(dtos);
    }

//
//    // 사용자 기준 댓글 조회 추가
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<List<CommentResponseDto>> findCommentsByUserId(@PathVariable Long userId) {
//        return ResponseEntity.ok(commentService.findCommentsByUserId(userId));
//    }


    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> update(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequestDto updateRequestDto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);
        return ResponseEntity.ok(commentService.update(userId, commentId, updateRequestDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable("commentId") Long commentId
    ) {
        Long userId = JwtUtil.extractUserId(authorization);
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.ok().build();
    }



}
