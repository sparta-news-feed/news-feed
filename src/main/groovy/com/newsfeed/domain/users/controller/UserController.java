package com.newsfeed.domain.users.controller;

import com.newsfeed.common.Const;
import com.newsfeed.common.dto.response.MessageResponse;
import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.users.dto.request.UserDeleteRequestDto;
import com.newsfeed.domain.users.dto.request.UserLoginRequestDto;
import com.newsfeed.domain.users.dto.request.UserPasswordUpdateRequestDto;
import com.newsfeed.domain.users.dto.request.UserSignUpRequestDto;
import com.newsfeed.domain.users.dto.response.UserProfileResponseDto;
import com.newsfeed.domain.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Const.ROOT_API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<UserProfileResponseDto> signUp(@Valid @RequestBody UserSignUpRequestDto requestDto) {
        UserProfileResponseDto response = userService.signUp(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getUsername(),
                requestDto.getAddress()
        );
        return ResponseEntity.ok(response);
    }

    // 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        UserProfileResponseDto response = userService.login(requestDto.getEmail(), requestDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.generateToken(response.getId()));

        return new ResponseEntity<>(new MessageResponse("로그인에 성공했습니다."), httpHeaders, HttpStatus.OK);
    }

    // 유저 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestHeader(name = "Authorization") String authorization) {
        Long userId = JwtUtil.extractUserId(authorization);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.invalidateToken(userId));

        return new ResponseEntity<>(new MessageResponse("로그아웃에 성공했습니다."), httpHeaders, HttpStatus.OK);
    }

    // 유저 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> findById(@PathVariable(name = "id") Long id) {
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
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody UserDeleteRequestDto requestDto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);     // 로그인한 유저 Token 가져와서 id로 받기
        userService.deleteUser(userId, requestDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.invalidateToken(userId));

        return new ResponseEntity<>(new MessageResponse("회원탈퇴에 성공했습니다."), httpHeaders, HttpStatus.OK);
    }
}

