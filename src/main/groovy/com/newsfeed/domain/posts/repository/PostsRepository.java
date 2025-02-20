package com.newsfeed.domain.posts.repository;

import com.newsfeed.domain.posts.entity.Posts;
import com.newsfeed.domain.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Posts> findByUserInOrderByCreatedAtDesc(List<User> user, Pageable pageable);

    List<Posts> findAllByUser(User user);
}

