package com.newsfeed.domain.followers.entity;

import com.newsfeed.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//{
//    id: 1,
//    following: {
//
//    },
//
//}

@Entity
@Getter
@NoArgsConstructor
@Table(name = "followers")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_user_id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    private User following;

    public Follower(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

    public Follower(User following) {
        this.following = following;
    }
}
