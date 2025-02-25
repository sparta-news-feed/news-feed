package com.newsfeed.domain.posts.service;

import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.config.PasswordEncoder;
import com.newsfeed.domain.comment.repository.CommentRepository;
import com.newsfeed.domain.followers.entity.Follower;
import com.newsfeed.domain.followers.repository.FollowerRepository;
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsDeleteRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.*;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.posts.repository.PostsRepository;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
import com.newsfeed.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class PostsService {

    private final PostsRepository postsRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final FollowerRepository followerRepository;

    @Transactional
    public PostsCreateResponseDto create(Long userId, PostsCreateRequestDto dto) {
        // userId로 유저 찾기
        User findUser = userService.findUserByIdOrElseThrow(userId);

        // dto와 찾은 유저로 게시물 객체 생성
        Posts posts = new Posts(
                dto.getTitle(),
                dto.getContents(),
                findUser
        );

        // 생성한 게시물 객체 db에 저장
        Posts savedPosts = postsRepository.save(posts);

        // dto 형태로 반환
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
    public PostsPageResponseDto findAll(LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        // page를 0이하로 입력하면 첫번째 페이지 반환
        int adjustedPage = (page > 0) ? page - 1 : 0;
        // 수정일 기준으로 내림차순 정렬
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("modifiedAt").descending());
        Page<Posts> postsPage;

        // startDate와 endDate를 입력했을 때 startDate가 endDate 보다 뒤면 예외처리
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ApplicationException("시작 날짜는 종료 날짜보다 이후일 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // startDate와 endDate 둘 중 하나라도 null 이면 전체 기간 조회
        if (startDate == null || endDate == null) {
            postsPage = postsRepository.findAll(pageable);
        } else {
            // startDate와 endDate 둘 다 입력하면 두 기간 사이 조회

            postsPage = postsRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        }

        Page<PostsResponseDto> responseDto = postsPage.map(posts -> new PostsResponseDto(
                posts.getPostId(),
                posts.getUser().getUsername(),
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        ));

        return new PostsPageResponseDto(responseDto);
    }

    @Transactional(readOnly = true)
    public PostsPageResponseDto findPostByFollowing(Long userId, int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;

        User findUser = userRepository.findByIdOrElseThrow(userId);
        List<Follower> followList = followerRepository.findAllByFollower(findUser);

        List<User> followingUsers = followList.stream()
                .map(Follower::getFollowing)
                .collect(Collectors.toList());

        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("modifiedAt").descending());

        Page<PostsResponseDto> responseDto = postsRepository.findByUserInOrderByCreatedAtDesc(followingUsers, pageable)
                .map(posts -> new PostsResponseDto(
                        posts.getPostId(),
                        posts.getUser().getUsername(),
                        posts.getTitle(),
                        posts.getContents(),
                        posts.getCreatedAt(),
                        posts.getModifiedAt()
                ));

        return new PostsPageResponseDto(responseDto);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findOne(Long postId) {
        // postId로 게시물 확인 후 없으면 예외 처리
        Posts posts = findPostByIdOrElseThrow(postId);
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
        // userId로 유저 확인 후 없으면 예외 처리
        User findUser = userService.findUserByIdOrElseThrow(userId);
        // postId로 게시물 확인 후 없으면 예외 처리
        Posts posts = findPostByIdOrElseThrow(postId);

        if (!posts.getUser().getId().equals(userId)) {
            throw new ApplicationException("자신이 작성한 게시물만 수정이 가능합니다.", HttpStatus.FORBIDDEN);
        }

        // 찾은 유저의 비밀번호와 입력한 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            // 일치하지 않으면 예외처리
            throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 일치하면 게시물 수정
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
    public void deleteById(Long userId, PostsDeleteRequestDto dto) {
        User findUser = userService.findUserByIdOrElseThrow(userId);
        Posts posts = findPostByIdOrElseThrow(dto.getPostId());

        if (!posts.getUser().getId().equals(userId)) {
            throw new ApplicationException("자신이 작성한 게시물만 삭제가 가능합니다.", HttpStatus.FORBIDDEN);
        }

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        postsRepository.delete(posts);
    }

    // Service 레벨에서 NULL 체크 (포스트 ID)
    public Posts findPostByIdOrElseThrow(Long postId) {
        return postsRepository.findById(postId)
                .orElseThrow(() -> new ApplicationException("해당 아이디와 일치하는 게시물이 없습니다. id = " + postId, HttpStatus.NOT_FOUND));
    }
}
