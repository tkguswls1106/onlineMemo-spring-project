package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.user.UserJoinRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.service.logic.MemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserAndMemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    MemoJpaRepository memoJpaRepository;
    @Autowired
    UserAndMemoJpaRepository userAndMemoJpaRepository;
    @Autowired
    UserServiceLogic userServiceLogic;
    @Autowired
    MemoServiceLogic memoServiceLogic;
    @Autowired
    UserAndMemoServiceLogic userAndMemoServiceLogic;


    @Test
    @DisplayName("사용자 회원가입_Test")
    void userSave_Test() {
        String firstPw = "1차비번입력함";
        String loginId = "로그인아이디입력함";
        String secondPw = "2차비번입력함";
        String username = "사용자이름입력함";

        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .firstPw(firstPw)
                .loginId(loginId)
                .secondPw(secondPw)
                .username(username)
                .build();

        userServiceLogic.save(userJoinRequestDto);
    }

    @Test
    @DisplayName("사용자 1명 검색_Test")
    void findUser_Test() {  // userId로 검색한 사용자 1명 반환 기능.
        UserResponseDto userResponseDto = userServiceLogic.findById(Long.valueOf(3));  // userId가 3인 사용자 검색
        assertThat(userResponseDto.getId()).isEqualTo(Long.valueOf(3));  // userId가 3이 맞는가?
        assertThat(userResponseDto.getLoginId()).isEqualTo("2tktktk");  // loginId가 '2tktktk'이 맞는가?
        assertThat(userResponseDto.getUsername()).isEqualTo("2shj");  // username이 '2shj'이 맞는가?
    }
}
