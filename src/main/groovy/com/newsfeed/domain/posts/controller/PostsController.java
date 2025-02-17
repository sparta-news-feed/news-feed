package com.newsfeed.domain.posts.controller;

import com.newsfeed.common.Const;
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Const.ROOT_API_PATH + "/posts")
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<PostsCreateResponseDto> create(@RequestBody PostsCreateRequestDto dto) {
        return ResponseEntity.ok(postsService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<PostsResponseDto>> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<PostsResponseDto> responseDto = postsService.findAll(page, size);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostsResponseDto> findOne(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.findOne(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostsUpdateResponseDto> update(
            //@SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable("postId") Long postId,
            @RequestBody PostsUpdateRequestDto dto
    ) {
        return  ResponseEntity.ok(postsService.update(postId, dto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            //@SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable("postId") Long postId
    ) {
        postsService.deleteById(postId); //+id
        return ResponseEntity.ok().build();
    }



}
