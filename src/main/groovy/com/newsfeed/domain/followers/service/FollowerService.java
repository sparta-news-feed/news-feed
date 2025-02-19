package com.newsfeed.domain.followers.service;

import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.domain.followers.dto.FollowingRequestDto;
import com.newsfeed.domain.followers.entity.Follower;
import com.newsfeed.domain.followers.repository.FollowerRepository;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
import com.newsfeed.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserService userService;

    public void addFollowing(Long userId, Long followingUserId) {
        User findUser = userService.findUserByIdOrElseThrow(userId);
        User followingUser = userService.findUserByIdOrElseThrow(followingUserId);

        // 팔로워 테이블에 팔로잉(내가 추가한 유저 한명) 생성.
        Follower follower = new Follower(findUser, followingUser);
        followerRepository.save(follower);
    }

    // 언팔로우
    public void deleteFollowing(Long userId, Long followingUserId) {
        Follower follower = followerRepository.findByFollower_IdAndFollowing_Id(userId, followingUserId)
                .orElseThrow(() -> new ApplicationException("해당하는 유저가 없습니다. followingUserId = " + followingUserId, HttpStatus.NOT_FOUND));
        followerRepository.delete(follower);
    }
}
