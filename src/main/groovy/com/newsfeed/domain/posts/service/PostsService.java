package com.newsfeed.domain.posts.service;


import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.posts.repository.PostsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public PostsCreateResponseDto create(PostsCreateRequestDto dto) {

        Posts posts = new Posts(
                dto.getTitle(),
                dto.getContents()
        );
        Posts savedPosts = postsRepository.save(posts);

        return new PostsCreateResponseDto(
                savedPosts.getPostId(),
                savedPosts.getTitle(),
                savedPosts.getContents(),
                savedPosts.getCreatedAt(),
                savedPosts.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> findAll(LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        int adjustedPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("modifiedAt").descending());
        Page<Posts> postsPage;

        if (startDate == null || endDate == null) {
            postsPage = postsRepository.findAll(pageable);
        } else {
            postsPage = postsRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        }
        return postsPage.map(posts -> new PostsResponseDto(
                posts.getPostId(),
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        ));
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findOne(Long postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));
        return new PostsResponseDto(
                posts.getPostId(),
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        );
    }

    @Transactional
    public PostsUpdateResponseDto update(Long postId, PostsUpdateRequestDto dto) {
        Posts posts = postsRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));

        /// if (!userId.equals(posts.getUser().getId())) {
        ///             throw new IllegalArgumentException("본인이 작성한 스케줄만 수정할 수 있습니다.");
        ///         }

        posts.update(
                dto.getTitle(),
                dto.getContents()
        );

        return new PostsUpdateResponseDto(
                posts.getPostId(),
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        );
    }

    @Transactional
    public void deleteById(Long postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));
        //if (!userId.equals(posts.getUser().getId())) {
        //            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
        //        }
        postsRepository.delete(posts);
    }
}
