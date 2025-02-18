package com.newsfeed.domain.comment.service;

import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.comment.dto.request.CommentCreateRequestDto;
import com.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.newsfeed.domain.comment.entity.Comment;
import com.newsfeed.domain.comment.repository.CommentRepository;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.posts.repository.PostsRepository;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public CommentResponseDto createComment(Long userId, CommentCreateRequestDto requestDto) {
        User findUser = userRepository.findByUserIdOrElseThrow(userId); //유저 찾기

        Posts findPosts = postsRepository.findById(requestDto.getPostId()) //
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 댓글 객체생성
        Comment comment = new Comment(
                findUser,
                findPosts,
                requestDto.getContents()
        );

        //댓글 저장 ???
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

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComment(

    ) {
    }
}
