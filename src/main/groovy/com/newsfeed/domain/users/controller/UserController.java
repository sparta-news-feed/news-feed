package com.newsfeed.domain.users.controller;

import com.newsfeed.common.Const;
import com.newsfeed.common.dto.response.MessageResponse;
import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.users.dto.request.UserDeleteRequestDto;
import com.newsfeed.domain.users.dto.request.UserLoginRequestDto;
import com.newsfeed.domain.users.dto.request.UserPasswordUpdateRequestDto;
import com.newsfeed.domain.users.dto.request.UserSignUpRequestDto;
import com.newsfeed.domain.users.dto.response.UserFollowingsProfileResponseDto;
import com.newsfeed.domain.users.dto.response.UserProfileResponseDto;
import com.newsfeed.domain.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<MessageResponse> login(
            @RequestHeader(name = "Authorization", required = false) String authorization,
            @Valid @RequestBody UserLoginRequestDto requestDto
    ) {
        if (authorization != null) {
            if (JwtUtil.validateExpired(authorization)) {
                throw new ApplicationException("이미 만료된 토큰입니다. 로그인을 다시 시도해주세요", HttpStatus.UNAUTHORIZED);
            }

            throw new ApplicationException("이미 로그인된 유저입니다.", HttpStatus.BAD_REQUEST);
        }

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
    public ResponseEntity<UserProfileResponseDto> findById(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable(name = "id") Long id) {
        Long userId = JwtUtil.extractUserId(authorization);
        UserProfileResponseDto userProfileResponseDto = userService.findById(userId, id);
        return new ResponseEntity<>(userProfileResponseDto, HttpStatus.OK);
    }

    // 유저 비밀번호 수정
    @PatchMapping
    public ResponseEntity<MessageResponse> updatePassword(
            @RequestHeader(name = "Authorization") String authorization,
            @Valid @RequestBody UserPasswordUpdateRequestDto requestDto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);
        userService.updatePassword(userId, requestDto.getOldPassword(), requestDto.getNewPassword());

        // 비밀번호 수정시에 토큰 만료시켜서 재로그인 유도
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.invalidateToken(userId));

        return new ResponseEntity<>(new MessageResponse("비밀번호 변경에 성공했습니다."), httpHeaders, HttpStatus.OK);
    }

    //팔로잉 목록 조회
    @GetMapping("/followings")
    public ResponseEntity<List<UserFollowingsProfileResponseDto>> getFollowingList(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestHeader(name = "Authorization") String authorization
    ) {
        Long getUserId = (userId != null) ? userId : JwtUtil.extractUserId(authorization);
        List<UserFollowingsProfileResponseDto> response = userService.getFollowingList(getUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //팔로워 목록 조회
    @GetMapping("/followers")
    public ResponseEntity<List<UserFollowingsProfileResponseDto>> getFollowerList(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestHeader(name = "Authorization") String authorization
    ) {
        Long getUserId = (userId != null) ? userId : JwtUtil.extractUserId(authorization);
        List<UserFollowingsProfileResponseDto> response = userService.getFollowerList(getUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 유저 회원 탈퇴
    @PostMapping("/withdraw")
    public ResponseEntity<MessageResponse> deleteUser(
            @RequestHeader(name = "Authorization") String authorization,    // 로그인한 유저 쓰려면 필수
            @RequestBody UserDeleteRequestDto requestDto
    ) {
        Long userId = JwtUtil.extractUserId(authorization);     // 로그인한 유저 Token 가져와서 id로 받기
        userService.deleteUser(userId, requestDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + JwtUtil.invalidateToken(userId));

        return new ResponseEntity<>(new MessageResponse("회원탈퇴에 성공했습니다."), httpHeaders, HttpStatus.OK);
    }
}

