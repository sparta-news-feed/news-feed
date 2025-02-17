package com.newsfeed.domain.users.dto.response;

import com.newsfeed.domain.users.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
// 유저 팔로잉 목록 조회 Dto
@Getter
public class UserFollowingsProfileResponseDto {
  private Long id;
  private String email;
  private String username;
  private String address;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public UserFollowingsProfileResponseDto(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.username = user.getUsername();
    this.address = user.getAddress(); // null 가능
    this.createdAt = user.getCreatedAt();
    this.modifiedAt = user.getModifiedAt();
  }
}
