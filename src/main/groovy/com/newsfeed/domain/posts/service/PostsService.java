package com.newsfeed.domain.posts.service;


import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.config.PasswordEncoder;
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsDeleteRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.posts.repository.PostsRepository;

import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PostsCreateResponseDto create(Long userId, PostsCreateRequestDto dto) {
        User findUser = userRepository.findByIdOrElseThrow(userId);
        Posts posts = new Posts(
                dto.getTitle(),
                dto.getContents(),
                findUser
        );
        Posts savedPosts = postsRepository.save(posts);

        return new PostsCreateResponseDto(
                savedPosts.getPostId(),
                savedPosts.getUser().getUsername(),
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

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ApplicationException("시작 날짜는 종료 날짜보다 이후일 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        if (startDate == null || endDate == null) {
            postsPage = postsRepository.findAll(pageable);
        } else {
            postsPage = postsRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        }
        return postsPage.map(posts -> new PostsResponseDto(
                posts.getPostId(),
                posts.getUser().getUsername(),
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
                posts.getUser().getUsername(),
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        );
    }

    @Transactional
    public PostsUpdateResponseDto update(Long userId, Long postId, PostsUpdateRequestDto dto) {
        User findUser = userRepository.findByIdOrElseThrow(userId);

        Posts posts = postsRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        posts.update(
                dto.getTitle(),
                dto.getContents()
        );

        return new PostsUpdateResponseDto(
                posts.getPostId(),
                posts.getUser().getUsername(),
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        );
    }

    @Transactional
    public void deleteById(Long userId, Long postId, PostsDeleteRequestDto dto) {
        User findUser = userRepository.findByIdOrElseThrow(userId);
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        postsRepository.delete(posts);
    }
}
