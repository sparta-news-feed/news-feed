package com.newsfeed.domain.comment.service;

import com.newsfeed.domain.comment.dto.request.CommentCreateRequestDto;
import com.newsfeed.domain.comment.dto.request.CommentUpdateRequestDto;
import com.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.newsfeed.domain.comment.dto.response.CommentUpdateResponseDto;
import com.newsfeed.domain.comment.entity.Comment;
import com.newsfeed.domain.comment.repository.CommentRepository;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.posts.repository.PostsRepository;
import com.newsfeed.domain.posts.service.PostsService;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostsService postsService; //레포지터리에서 서비스로 수정함
    private final UserService userService; //레포지터리에서 서비스로 수정함
    private final PostsRepository postsRepository;

    public CommentResponseDto createComment(Long userId, CommentCreateRequestDto requestDto) {
        User findUser = userService.findUserByIdOrElseThrow(userId);//유저 찾기

        Posts findPosts = postsService.findPostByIdOrElseThrow(requestDto.getPostId());

        // 댓글 객체생성
        Comment comment = new Comment(
                findUser,
                findPosts,
                requestDto.getContents()
        );

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getCommentId(),
                savedComment.getPosts().getPostId(),
                savedComment.getUser().getUsername(),
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }


    // 논의 사항 - 전체 조회 기능 필요성 유무 :
    // 1. post 또는 user 기준으로 조회 하는 것이 더 필요하지 않은가?
    // 2. 전체 조회를 한다면 키워드 기준으로 조회가 되게 하는게 맞지 않나? << 이거 할 자신 없음
    // 3. 만약 댓글 전체 조회 기능을 유지한다면 토큰으로 로그인 한 사람만 조회하게 해야하는가?
//    @Transactional(readOnly = true)
//    public List<CommentResponseDto> findAllComment() {
//
//        List<Comment> comments = commentRepository.findAll();
//
//        List<CommentResponseDto> dtos = comments.stream().map(
//                comment -> new CommentResponseDto(
//                        comment.getCommentId(),
//                        comment.getPosts().getPostId(),
//                        comment.getUser().getUsername(),
//                        comment.getContents(),
//                        comment.getCreatedAt(),
//                        comment.getModifiedAt()
//                )
//        ).toList();
//        return dtos;
//    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findPostComment(Long postId) {

        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findByPosts(posts); // 레포지터리 추가

                List<CommentResponseDto> dtos = comments.stream().map(
                comment -> new CommentResponseDto(
                        comment.getCommentId(),
                        comment.getPosts().getPostId(),
                        comment.getUser().getUsername(),
                        comment.getContents(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )
        ).toList();
        return dtos;

    }

    @Transactional
    public CommentUpdateResponseDto update(Long userId, Long commentId, CommentUpdateRequestDto updateRequestDto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        if (comment.getUser() == null || !comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        comment.update(
                updateRequestDto.getContents()
        );

        return new CommentUpdateResponseDto(
                comment.getCommentId(),
                comment.getPosts().getPostId(),
                comment.getUser().getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );

    }

    @Transactional
    public void deleteComment(Long userId, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        if (comment.getUser() == null || !comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);


    }




}
