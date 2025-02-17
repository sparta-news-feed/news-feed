package com.newsfeed.domain.posts.controller;

import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostsController {

    private final PostsService postsService;

    @PostMapping("/posts")
    public ResponseEntity<PostsCreateResponseDto> create(@RequestBody PostsCreateRequestDto dto) {
        return ResponseEntity.ok(postsService.create(dto));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostsResponseDto>> findAll() {
        return ResponseEntity.ok(postsService.findAll());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostsResponseDto> findOne(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.findOne(postId));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostsUpdateResponseDto> update(
            //@SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable("postId") Long postId,
            @RequestBody PostsUpdateRequestDto dto
    ) {
        return  ResponseEntity.ok(postsService.update(postId, dto));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> delete(
            //@SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable("postId") Long postId
    ) {
        postsService.deleteById(postId); //+id
        return ResponseEntity.ok().build();
    }



}
