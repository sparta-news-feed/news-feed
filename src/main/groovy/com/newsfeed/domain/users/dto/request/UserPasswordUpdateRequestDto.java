package com.newsfeed.domain.users.dto.request;

import lombok.Getter;

@Getter
public class UserPasswordUpdateRequestDto {
  private String oldPassword;
  private String newPassword;

  UserPasswordUpdateRequestDto(String oldPassword,String newPassword){
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
  }
}
