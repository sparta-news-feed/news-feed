package com.newsfeed.domain.users.dto.response;
import com.newsfeed.domain.users.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserProfileResponseDto {
  private Long id;
  private String email;
  private String username;
  private String address;
  private Long followerCount;
  private Long followingCount;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

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
