package com.newsfeed.domain.users.service;

import com.newsfeed.common.Const;
import com.newsfeed.common.dto.response.MessageResponse;
import com.newsfeed.common.exception.ApplicationException;
import com.newsfeed.config.PasswordEncoder;
import com.newsfeed.domain.followers.repository.FollowerRepository;
import com.newsfeed.domain.users.dto.response.UserProfileResponseDto;
import com.newsfeed.domain.users.entity.User;
import com.newsfeed.domain.users.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserProfileResponseDto signUp(String email, String password, String username, String address) {
        if (existsByEmail(email)) {
            throw new ApplicationException("이미 사용중인 이메일입니다.", HttpStatus.CONFLICT);
        }

        User user = new User(email, passwordEncoder.encode(password), username, address);
        userRepository.save(user);
        return new UserProfileResponseDto(user, 0L, 0L);
    }

    // 유저 로그인
    public UserProfileResponseDto login(String email, String password) {
        User findUser = findByEmailOrThrow(email);

        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new ApplicationException("비밀번호가 일치하지 않습니다", HttpStatus.UNAUTHORIZED);
        }

        return new UserProfileResponseDto(findUser, 0L, 0L);
    }

    // 유저 단건 조회
    public UserProfileResponseDto findById(Long id) {
        // 조회하려는 유저 찾기
        User findUser = findByIdOrElseThrow(id);
        return new UserProfileResponseDto(findUser, 0L, 0L);
    }

    // 유저 비밀번호 수정
    public void updatePassword(String oldPassword, String newPassword) {
        //JWT에서 내 로그인 정보 가져오기

        // 이메일을 이용해 유저 조회
//        User findUser = userRepository.findByEmail(email);
        User findUser = new User();

        // 비밀번호 검증
        if (!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 암호화해서 저장
        findUser.updatePassword(passwordEncoder.encode(newPassword));
        userRepository.save(findUser);

    }

    @Transactional
    public void deleteUser(Long userId, String password) {
        User findUser = findByIdOrElseThrow(userId);

        if (findUser.getDeletedAt() != null) {
            throw new ApplicationException("이미 탈퇴한 사용자입니다.", HttpStatus.BAD_REQUEST);
        }

        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new ApplicationException("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        findUser.deleteUser();
    }

    // 해당 이메일을 가진 유저가 있는지 조회
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Service 레벨에서 NULL 체크(유저 ID)
    public User findByIdOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException("해당 아이디와 일치하는 유저가 없습니다. id = " + userId, HttpStatus.NOT_FOUND));
    }

    // Service 레벨에서 NULL 체크(유저 이메일)
    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApplicationException("해당 이메일과 일치하는 유저가 없습니다. email = " + email, HttpStatus.UNAUTHORIZED));
    }
}
