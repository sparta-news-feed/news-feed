package com.newsfeed.domain.posts.controller;

import com.newsfeed.common.Const;
<<<<<<< HEAD
import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsDeleteRequestDto;
=======
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
>>>>>>> feat/test
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsPageResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
=======
import org.springframework.cglib.core.Local;
>>>>>>> feat/test
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
<<<<<<< HEAD
    public ResponseEntity<PostsCreateResponseDto> create(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody PostsCreateRequestDto dto) {

        Long userId = JwtUtil.extractUserId(authorization);
        return ResponseEntity.ok(postsService.create(userId, dto));
    }

    @GetMapping("/find/all")
=======
    public ResponseEntity<PostsCreateResponseDto> create(@RequestBody PostsCreateRequestDto dto) {
        return ResponseEntity.ok(postsService.create(dto));
    }

    @GetMapping
>>>>>>> feat/test
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

<<<<<<< HEAD
    @GetMapping("/find/{postId}")
=======
    @GetMapping("/{postId}")
>>>>>>> feat/test
    public ResponseEntity<PostsResponseDto> findOne(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.findOne(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostsUpdateResponseDto> update(
<<<<<<< HEAD
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable("postId") Long postId,
            @RequestBody PostsUpdateRequestDto dto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);
        return  ResponseEntity.ok(postsService.update(userId, postId, dto));
=======
            //@SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable("postId") Long postId,
            @RequestBody PostsUpdateRequestDto dto
    ) {
        return  ResponseEntity.ok(postsService.update(postId, dto));
>>>>>>> feat/test
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
<<<<<<< HEAD
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable("postId") Long postId,
            @RequestBody PostsDeleteRequestDto dto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);

        postsService.deleteById(userId, postId, dto);
        return ResponseEntity.ok().build();
    }
=======
            //@SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable("postId") Long postId
    ) {
        postsService.deleteById(postId); //+id
        return ResponseEntity.ok().build();
    }



>>>>>>> feat/test
}
