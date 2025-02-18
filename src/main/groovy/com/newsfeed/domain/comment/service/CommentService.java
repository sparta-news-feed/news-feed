package com.newsfeed.domain.comment.service;

import com.newsfeed.common.utils.JwtUtil;
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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public CommentResponseDto createComment(Long userId, Long postId, String contents) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다.")); //jwt 사용해야하나요?

        Posts findPosts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .user(user)
                .posts(posts)
                .contents(contents)
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
