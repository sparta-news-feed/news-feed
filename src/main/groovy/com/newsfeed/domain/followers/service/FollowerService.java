package com.newsfeed.domain.followers.service;

import com.newsfeed.domain.followers.dto.FollowingRequestDto;
import com.newsfeed.domain.followers.entity.Follower;
import com.newsfeed.domain.followers.repository.FollowerRepository;
import com.newsfeed.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;





    public void addFollowing(Follower following) {

        // 팔로워 테이블에 팔로잉(내가 추가한 유저 한명) 생성.
        Follower follower = new Follower(following.getFollowing());
        followerRepository.save(follower);


    }
    // 언팔로우
    public void deleteFollowing(Follower following) {
        Follower deleteFollowing = followerRepository.findFollowingByFollowerOrElseThrow(following);
        followerRepository.delete(deleteFollowing);
    }
}
