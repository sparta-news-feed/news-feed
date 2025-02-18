package com.newsfeed.domain.posts.controller;

import com.newsfeed.common.Const;
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsPageResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<PostsCreateResponseDto> create(@RequestBody PostsCreateRequestDto dto) {
        return ResponseEntity.ok(postsService.create(dto));
    }

    @GetMapping
    public ResponseEntity<PostsPageResponseDto> findAll(
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<PostsResponseDto> responseDto = postsService.findAll(startDate, endDate, page, size);
        PostsPageResponseDto pageDto = new PostsPageResponseDto(responseDto);
        return ResponseEntity.ok(pageDto);
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
