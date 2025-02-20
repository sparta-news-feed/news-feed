package com.newsfeed.domain.comment.service;

import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.domain.comment.dto.request.CommentCreateRequestDto;
import com.newsfeed.domain.comment.dto.request.CommentUpdateRequestDto;
import com.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.newsfeed.domain.comment.dto.response.CommentUpdateResponseDto;
import com.newsfeed.domain.comment.entity.Comment;
import com.newsfeed.domain.comment.repository.CommentRepository;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.posts.service.PostsService;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostsService postsService; //레포지터리에서 서비스로 수정함
    private final UserService userService; //레포지터리에서 서비스로 수정함

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
        findPosts.setComments(savedComment);

        return new CommentResponseDto(
                savedComment.getCommentId(),
                savedComment.getPosts().getPostId(),
                savedComment.getUser().getUsername(),
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findPostComment(Long postId) {

        Posts posts = postsService.findPostByIdOrElseThrow(postId);

        List<Comment> comments = commentRepository.findByPosts(posts); // 레포지터리 추가

        List<CommentResponseDto> dtos = comments
                .stream()
                .map(comment -> new CommentResponseDto(
                                comment.getCommentId(),
                                comment.getPosts().getPostId(),
                                comment.getUser().getUsername(),
                                comment.getContents(),
                                comment.getCreatedAt(),
                                comment.getModifiedAt()
                        )
                )
                .toList();

        return dtos;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findUserComment(Long id) {

        User user = userService.findUserByIdOrElseThrow(id);

        List<Comment> comments = commentRepository.findByUser(user);

        List<CommentResponseDto> dtos = comments
                .stream()
                .map(
                        comment -> new CommentResponseDto(
                                comment.getCommentId(),
                                comment.getPosts().getPostId(),
                                comment.getUser().getUsername(),
                                comment.getContents(),
                                comment.getCreatedAt(),
                                comment.getModifiedAt()
                        )
                )
                .toList();

        return dtos;
    }

    @Transactional
    public CommentUpdateResponseDto update(Long userId, Long commentId, CommentUpdateRequestDto updateRequestDto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new ApplicationException("댓글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
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

        if (!comment.getUser().getId().equals(userId)) {
            throw new ApplicationException("댓글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }

}
