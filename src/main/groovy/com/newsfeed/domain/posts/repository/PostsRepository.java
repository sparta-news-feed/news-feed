package com.newsfeed.domain.posts.repository;

import com.newsfeed.domain.posts.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    default Posts findByPostsIdOrElseThrow(Long postsId) {
        return  findById(postsId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 뉴스가 존재하지 않습니다."));
    }
}
