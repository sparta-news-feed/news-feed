package com.newsfeed.domain.users.repository;

import com.newsfeed.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);

  default User findByIdOrElseThrow(Long userId) {
    return  findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디와 일치하는 유저가 없습니다. id = " + userId));
  }
}
