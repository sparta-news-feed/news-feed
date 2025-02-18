package com.newsfeed.domain.posts.repository;

import com.newsfeed.domain.posts.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
