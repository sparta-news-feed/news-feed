package com.newsfeed.domain.users.dto.request;

import com.newsfeed.common.Const;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordUpdateRequestDto {
  private String oldPassword;
  @Pattern(regexp = Const.PASSWORD_PATTERN, message = "비밀번호 형식이 일치하지 않습니다.")
  private String newPassword;

  UserPasswordUpdateRequestDto(String oldPassword,String newPassword){
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
  }
}
