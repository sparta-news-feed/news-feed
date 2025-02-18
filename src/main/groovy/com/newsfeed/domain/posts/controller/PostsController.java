package com.newsfeed.domain.posts.controller;

import com.newsfeed.common.Const;
import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsDeleteRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsPageResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(Const.ROOT_API_PATH + "/posts")
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<PostsCreateResponseDto> create(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody PostsCreateRequestDto dto) {

        Long userId = JwtUtil.extractUserId(authorization);
        return ResponseEntity.ok(postsService.create(userId, dto));
    }

    @GetMapping("/find/all")
    public ResponseEntity<PostsPageResponseDto> findAll(
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        PostsPageResponseDto responseDto = postsService.findAll(startDate, endDate, page, size);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/find/{postId}")
    public ResponseEntity<PostsResponseDto> findOne(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.findOne(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostsUpdateResponseDto> update(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable("postId") Long postId,
            @RequestBody PostsUpdateRequestDto dto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);
        return  ResponseEntity.ok(postsService.update(userId, postId, dto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable("postId") Long postId,
            @RequestBody PostsDeleteRequestDto dto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);

        postsService.deleteById(userId, postId, dto);
        return ResponseEntity.ok().build();
    }
}
