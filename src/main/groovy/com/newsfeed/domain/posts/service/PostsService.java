package com.newsfeed.domain.posts.service;


<<<<<<< HEAD
import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.config.PasswordEncoder;
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
import com.newsfeed.domain.posts.dto.request.PostsDeleteRequestDto;
=======
import com.newsfeed.domain.posts.dto.request.PostsCreateRequestDto;
>>>>>>> feat/test
import com.newsfeed.domain.posts.dto.request.PostsUpdateRequestDto;
import com.newsfeed.domain.posts.dto.response.PostsCreateResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsResponseDto;
import com.newsfeed.domain.posts.dto.response.PostsUpdateResponseDto;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.posts.repository.PostsRepository;

<<<<<<< HEAD
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
=======
>>>>>>> feat/test
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
=======
>>>>>>> feat/test
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class PostsService {

    private final PostsRepository postsRepository;
<<<<<<< HEAD
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PostsCreateResponseDto create(Long userId, PostsCreateRequestDto dto) {
        // userId로 유저 찾기
        User findUser = userRepository.findByUserIdOrElseThrow(userId);

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
=======

    @Transactional
    public PostsCreateResponseDto create(PostsCreateRequestDto dto) {

        Posts posts = new Posts(
                dto.getTitle(),
                dto.getContents()
        );
        Posts savedPosts = postsRepository.save(posts);

        return new PostsCreateResponseDto(
                savedPosts.getPostId(),
>>>>>>> feat/test
                savedPosts.getTitle(),
                savedPosts.getContents(),
                savedPosts.getCreatedAt(),
                savedPosts.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> findAll(LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
<<<<<<< HEAD
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
=======
        int adjustedPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("modifiedAt").descending());
        Page<Posts> postsPage;

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜는 종료 날짜보다 이후일 수 없습니다.");
        }

        if (startDate == null || endDate == null) {
            postsPage = postsRepository.findAll(pageable);
        } else {
>>>>>>> feat/test
            postsPage = postsRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        }
        return postsPage.map(posts -> new PostsResponseDto(
                posts.getPostId(),
<<<<<<< HEAD
                posts.getUser().getUsername(),
=======
>>>>>>> feat/test
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        ));
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findOne(Long postId) {
<<<<<<< HEAD
        // postId로 게시물 확인 후 없으면 예외 처리
        Posts posts = postsRepository.findByPostsIdOrElseThrow(postId);
        return new PostsResponseDto(
                posts.getPostId(),
                posts.getUser().getUsername(),
=======
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));
        return new PostsResponseDto(
                posts.getPostId(),
>>>>>>> feat/test
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        );
    }

    @Transactional
<<<<<<< HEAD
    public PostsUpdateResponseDto update(Long userId, Long postId, PostsUpdateRequestDto dto) {
        // userId로 유저 확인 후 없으면 예외 처리
        User findUser = userRepository.findByUserIdOrElseThrow(userId);
        // postId로 게시물 확인 후 없으면 예외 처리
        Posts posts = postsRepository.findByPostsIdOrElseThrow(postId);

        // 찾은 유저의 비밀번호와 입력한 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            // 일치하지 않으면 예외처리
            throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        // 일치하면 게시물 수정
=======
    public PostsUpdateResponseDto update(Long postId, PostsUpdateRequestDto dto) {
        Posts posts = postsRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));

        /// if (!userId.equals(posts.getUser().getId())) {
        ///             throw new IllegalArgumentException("본인이 작성한 스케줄만 수정할 수 있습니다.");
        ///         }

>>>>>>> feat/test
        posts.update(
                dto.getTitle(),
                dto.getContents()
        );

        return new PostsUpdateResponseDto(
                posts.getPostId(),
<<<<<<< HEAD
                posts.getUser().getUsername(),
=======
>>>>>>> feat/test
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt(),
                posts.getModifiedAt()
        );
    }

    @Transactional
<<<<<<< HEAD
    public void deleteById(Long userId, Long postId, PostsDeleteRequestDto dto) {
        // userId로 유저 확인 후 없으면 예외 처리
        User findUser = userRepository.findByUserIdOrElseThrow(userId);
        // postId로 게시물 확인 후 없으면 예외 처리
        Posts posts = postsRepository.findByPostsIdOrElseThrow(postId);

        // 찾은 유저의 비밀번호와 입력한 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            // 일치하지 않으면 예외처리
            throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        // 일치하면 게시물 삭제
=======
    public void deleteById(Long postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));
        //if (!userId.equals(posts.getUser().getId())) {
        //            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
        //        }
>>>>>>> feat/test
        postsRepository.delete(posts);
    }
}
