package com.newsfeed.domain.followers.repository;

import com.newsfeed.domain.followers.entity.Follower;
import com.newsfeed.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

  Long countByFollower(User findUser);

  Long countByFollowing(User findUser);
}
