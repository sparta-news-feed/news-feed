package com.newsfeed.domain.users.controller;

import com.newsfeed.common.Const;
import com.newsfeed.common.dto.response.MessageResponse;
import com.newsfeed.domain.users.dto.request.UserDeleteRequestDto;
import com.newsfeed.domain.users.dto.request.UserPasswordUpdateRequestDto;
import com.newsfeed.domain.users.dto.request.UserSignUpRequestDto;
import com.newsfeed.domain.users.dto.response.UserProfileResponseDto;
import com.newsfeed.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Const.ROOT_API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 회원 가입
    @PostMapping
    public ResponseEntity<UserProfileResponseDto> signUp(@RequestBody UserSignUpRequestDto requestDto) {
        UserProfileResponseDto response = userService.signUp(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getUsername(),
                requestDto.getAddress()
        );
        return ResponseEntity.ok(response);
    }

    // 유저 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> findById(@PathVariable Long id) {
        UserProfileResponseDto userProfileResponseDto = userService.findById(id);
        return new ResponseEntity<>(userProfileResponseDto, HttpStatus.OK);
    }

    // 유저 비밀번호 수정
    @PatchMapping
    public ResponseEntity<Void> updatePassword(@RequestBody UserPasswordUpdateRequestDto requestDto) {
        userService.updatePassword(requestDto.getOldPassword(), requestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteUser(
            @RequestHeader Long userId,
            @RequestBody UserDeleteRequestDto requestDto
    ) {
        MessageResponse response = userService.deleteUser(userId, requestDto.getPassword());
        return ResponseEntity.ok(response);
    }
}

