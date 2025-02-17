package com.newsfeed.domain.users.service;

import com.newsfeed.config.PasswordEncoder;
import com.newsfeed.domain.followers.repository.FollowerRepository;
import com.newsfeed.domain.users.dto.response.UserProfileResponseDto;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final FollowerRepository followerRepository;
  private final PasswordEncoder passwordEncoder;


  // 유저 단건 조회
  public UserProfileResponseDto findById(Long id) {
    //JWT에서 현재 로그인한 유저 정보 가져오기


    // 조회하려는 유저 찾기
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }
    User findUser = optionalUser.get();

    // 현재 로그인한 유저와 조회하려는 유저 같은지 비교

    // 다르면 널처리

    // 팔로워 & 팔로우 수 조회
    Long followerCount = followerRepository.countByFollower(findUser);
    Long followingCount = followerRepository.countByFollowing(findUser);

    return new UserProfileResponseDto(findUser, followerCount, followingCount);

  }

  // 유저 비밀번호 수정
  public Void updatePassword(String oldPassword, String newPassword) {
    //JWT에서 내 로그인 정보 가져오기

    // 이메일을 이용해 유저 조회
    User findUser = userRepository.findByEmail(email);

    // 비밀번호 검증
    if (!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }

    // 새 비밀번호 암호화해서 저장
    findUser.updatePassword(passwordEncoder.encode(newPassword));
    userRepository.save(findUser);


  }


}
