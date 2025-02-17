package com.newsfeed.domain.users.dto.response;
import com.newsfeed.domain.users.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
// 유저 단건 조회 Dto
@Getter
@RequiredArgsConstructor
public class UserProfileResponseDto {
  private final Long id;
  private final String email;
  private final String username;
  private final String address;
  private final Long followerCount;
  private final Long followingCount;
  private final LocalDateTime createdAt;
  private final LocalDateTime modifiedAt;

  public UserProfileResponseDto(User user, Long followerCount, Long followingCount) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.username = user.getUsername();
    this.address = user.getAddress(); // null 가능
    this.followerCount = followerCount;
    this.followingCount = followingCount;
    this.createdAt = user.getCreatedAt();
    this.modifiedAt = user.getModifiedAt();
  }


}
