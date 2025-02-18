package com.newsfeed.domain.users.service;

import com.newsfeed.common.Const;
import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.config.PasswordEncoder;
import com.newsfeed.domain.followers.entity.Follower;
import com.newsfeed.domain.followers.repository.FollowerRepository;
import com.newsfeed.domain.users.dto.response.UserFollowingsProfileResponseDto;
import com.newsfeed.domain.users.dto.response.UserProfileResponseDto;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final FollowerRepository followerRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UserProfileResponseDto signUp(String email, String password, String username, String address) {
    if (existsByEmail(email)) {
      throw new ApplicationException("이미 사용중인 이메일입니다.", HttpStatus.CONFLICT);
    }

    User user = new User(email, passwordEncoder.encode(password), username, address);
    userRepository.save(user);
    return new UserProfileResponseDto(user, 0L, 0L);
  }

  // 유저 로그인
  public UserProfileResponseDto login(String email, String password) {
    User findUser = findByEmailOrThrow(email);
    if (!passwordEncoder.matches(password, findUser.getPassword())) {
      throw new ApplicationException("비밀번호가 일치하지 않습니다", HttpStatus.UNAUTHORIZED);
    }
    return new UserProfileResponseDto(findUser, 0L, 0L);
  }

  // 유저 단건 조회
  public UserProfileResponseDto findById(Long userId, Long targetUserId) {

    User findUser = findUserById(targetUserId);
    // 팔로워와 팔로잉수 가져오기
    Long followerCount = followerRepository.countByFollowing(findUser);
    Long followingCount = followerRepository.countByFollower(findUser);
    // 로그인한 아이디(userId)와 조회 할 아이디(targetUserId)가 같을 경우 모두 출력
    if(userId.equals(targetUserId)) {
      return new UserProfileResponseDto(
          findUser.getId(),
          findUser.getEmail(),
          findUser.getUsername(),
          findUser.getAddress(),
          followerCount,
          followingCount,
          findUser.getCreatedAt(),
          findUser.getModifiedAt()
      );
    }

    // 로그인한 아이디(userId)와 조회 할 아이디(targetUserId)가 다를 경우 주소 null 처리
    return new UserProfileResponseDto(
        findUser.getId(),
        findUser.getEmail(),
        findUser.getUsername(),
        null,  // 주소만 `null` 처리
        followerCount,
        followingCount,
        findUser.getCreatedAt(),
        findUser.getModifiedAt()
    );
  }

  // 유저 비밀번호 수정
  public void updatePassword(Long userId, String oldPassword, String newPassword) {
    User findUser = findUserById(userId);
    // 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우 예외처리
    if (!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
      throw new ApplicationException("기존의 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
    // 비밀번호 형식이 올바르지 않은 경우 예외처리
    if (!isValidPassword(newPassword)){
      throw new ApplicationException("비밀번호 형식이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    // 현재 비밀번호와 동일한 비밀번호로 수정하는 경우
    if (passwordEncoder.matches(newPassword, findUser.getPassword())) {
      throw new ApplicationException("새 비밀번호를 기존의 비밀번호와 동일하게 변경할 수 없습니다.", HttpStatus.BAD_REQUEST);    }

    findUser.updatePassword(passwordEncoder.encode(newPassword));
    userRepository.save(findUser);

  }
  // 비밀번호 검증 메서드
  private boolean isValidPassword(String newPassword) {
    return newPassword.matches(Const.PASSWORD_PATTERN);
  }


  @Transactional
  public void deleteUser(Long userId, String password) {
    User findUser = findUserById(userId);
    if (findUser.getDeletedAt() != null) {
      throw new ApplicationException("이미 탈퇴한 사용자입니다.", HttpStatus.BAD_REQUEST);
    }

    if (!passwordEncoder.matches(password, findUser.getPassword())) {
      throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }

    findUser.deleteUser();
  }

  // 유저의 팔로잉 목록 조회 리스트
  public List<UserFollowingsProfileResponseDto> getFollowingList(Long userId) {
    User loginUser = findUserById(userId);
    List<Follower> followingList = followerRepository.findByFollower(loginUser); // 내가 팔로우한 사람들
    List<UserFollowingsProfileResponseDto> responseDtos = new ArrayList<>();

    for (Follower following : followingList) {
      responseDtos.add(new UserFollowingsProfileResponseDto(following.getFollowing())); // 내가 팔로우한 사람 정보 가져오기
    }
    return responseDtos;
  }

  // 유저의 팔로워 목록 조회
  public List<UserFollowingsProfileResponseDto> getFollowerList(Long userId) {
    User loginUser = findUserById(userId);
    List<Follower> followerList = followerRepository.findByFollowing(loginUser); // 나를 팔로우한 사람들
    List<UserFollowingsProfileResponseDto> responseDtos = new ArrayList<>();
    int a = 10;
    for (Follower follower : followerList) {
      responseDtos.add(new UserFollowingsProfileResponseDto(follower.getFollower())); // 나를 팔로우 한 사람 정보 가져오기
    }
    return responseDtos;
  }

  // 해당 이메일을 가진 유저가 있는지 조회
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  // Service 레벨에서 NULL 체크 (유저 ID)
  private User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ApplicationException("해당 아이디와 일치하는 유저가 없습니다. id = " + userId, HttpStatus.NOT_FOUND));
  }

  // Service 레벨에서 NULL 체크 (유저 이메일)
  private User findByEmailOrThrow(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ApplicationException("해당 이메일과 일치하는 유저가 없습니다. email = " + email, HttpStatus.UNAUTHORIZED));
  }
}
