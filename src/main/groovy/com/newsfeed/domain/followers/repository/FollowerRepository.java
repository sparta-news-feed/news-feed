package com.newsfeed.domain.followers.repository;

import com.newsfeed.domain.followers.entity.Follower;
import com.newsfeed.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
  // 특정 사용자의 팔로워 목록 조회
  @Query()
  List<Follower> findByFollowing(User following);

  // 특정 사용자의 팔로잉 목록 조회
  List<Follower> findByFollower(User follower);

  // 팔로워 수 조회
  Long countByFollowing(User following);

  // 팔로잉 수 조회
  Long countByFollower(User follower);

  Optional<Follower> findByFollower_IdAndFollowing_Id(Long followerUserId, Long followingUserId);

  List<Follower> findAllByFollower(User follower);
}
