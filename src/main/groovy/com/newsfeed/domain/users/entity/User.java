package com.newsfeed.domain.users.entity;

import com.newsfeed.common.entity.BaseEntity;
import com.newsfeed.domain.posts.entity.Posts;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;  // 비밀번호

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String address;

    // 회원 탈퇴 시간 저장 (NULL이면 탈퇴하지 않은 상태)
    @Column
    private LocalDateTime deletedAt = null;

    public User(String email, String password, String username, String address) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.address = address;
    }

    // 비밀번호 변경 메서드
    public void updatePassword(String password) {
        this.password = password;
    }

    public void deleteUser() {
        this.password = "";
        this.username = "";
        this.address = "";
        this.deletedAt = LocalDateTime.now();
    }

}
