package com.newsfeed.domain.users.controller;

import com.newsfeed.domain.users.dto.request.UserPasswordUpdateRequestDto;
import com.newsfeed.domain.users.dto.response.UserProfileResponseDto;
import com.newsfeed.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  // 유저 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<UserProfileResponseDto> findById(@PathVariable Long id){
    UserProfileResponseDto userProfileResponseDto = userService.findById(id);
    return new ResponseEntity<>(userProfileResponseDto, HttpStatus.OK);

  }

  // 유저 비밀번호 수정
  @PatchMapping
  public ResponseEntity<Void> updatePassword(@RequestBody UserPasswordUpdateRequestDto requestDto){
    userService.updatePassword(requestDto.getOldPassword(), requestDto.getNewPassword());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
