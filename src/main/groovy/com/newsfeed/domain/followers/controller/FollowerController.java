package com.newsfeed.domain.followers.controller;

import com.newsfeed.common.Const;
import com.newsfeed.common.dto.response.MessageResponse;
import com.newsfeed.common.utils.JwtUtil;
import com.newsfeed.domain.followers.dto.FollowingRequestDto;
import com.newsfeed.domain.followers.repository.FollowerRepository;
import com.newsfeed.domain.followers.service.FollowerService;
import com.newsfeed.domain.users.dto.request.UserDeleteRequestDto;
import com.newsfeed.domain.users.dto.response.UserFollowingsProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Const.ROOT_API_PATH + "/followers")
@RequiredArgsConstructor

public class FollowerController {

    private final FollowerService followerService;

    //  팔로잉 유저 추가
    @PostMapping
    public ResponseEntity<MessageResponse> addFollowing(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody FollowingRequestDto requestDto
    ) {
        Long userId = JwtUtil.extractUserId(authorization); // 무조건 팔로워 고정
        followerService.addFollowing(userId, requestDto.getFollowingUserId());
        return new ResponseEntity<>(new MessageResponse("팔로우에 성공했습니다."), HttpStatus.OK);
    }

    // 팔로잉 유저 삭제
    @DeleteMapping("/{followingUserId}")
    public ResponseEntity<MessageResponse> deleteFollowing(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable(name = "followingUserId") Long followingUserId
    ) {
        Long userId = JwtUtil.extractUserId(authorization);// 토큰 있는 사용자가
        followerService.deleteFollowing(userId, followingUserId); // 해당 유저를 삭제한다.
        return new ResponseEntity<>(new MessageResponse("언팔로우에 성공했습니다."), HttpStatus.OK);
    }

}
