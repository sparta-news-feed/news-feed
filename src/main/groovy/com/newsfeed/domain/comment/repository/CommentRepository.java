package com.newsfeed.domain.comment.repository;

import com.newsfeed.domain.comment.entity.Comment;
import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPosts(Posts posts);

    List<Comment> user(User user);

    List<Comment> findByUser(User user);

    void deleteByPosts(Posts posts);
}
