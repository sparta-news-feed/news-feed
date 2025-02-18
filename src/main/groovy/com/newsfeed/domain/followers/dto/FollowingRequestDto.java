package com.newsfeed.domain.followers.dto;

import com.newsfeed.domain.followers.entity.Follower;
import lombok.Getter;

@Getter
public class FollowingRequestDto {

    private final Follower following;

    public FollowingRequestDto(Follower following) {
        this.following = following;
    }
}
