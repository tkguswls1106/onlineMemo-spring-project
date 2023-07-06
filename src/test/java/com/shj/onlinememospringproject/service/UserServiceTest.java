package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.user.UserSignupRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.service.auth.AuthService;
import com.shj.onlinememospringproject.service.logic.MemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserAndMemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

// 사실 여기에 @Transactional 선언해도된다.
// 만약 롤백을 하지않고싶은 메소드가 따로 있다면, 헤당 테스트메소드에 각각 @Rollback(false)을 적어주면된다.
// @SpringBootTest
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
    @Autowired
    AuthService authService;


    // @Test
    @DisplayName("사용자 회원가입_Test")
    // @Transactional  // 테스트 코드에 @Transactional 사용하면 save 성공한뒤에 다시 롤백된다.
    void userSave_Test() {  // 신규 사용자 생성하고 userId 반환 기능.
        String firstPw = "1차비번입력함";
        String loginId = "로그인아이디입력함";
        //String secondPw = "2차비번입력함";
        String username = "사용자이름입력함";

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .firstPw(firstPw)
                .loginId(loginId)
                //.secondPw(secondPw)
                .username(username)
                .build();

//        assertThat(userSignupRequestDto.getFirstPw()).isEqualTo("1차비번입력함");
//        assertThat(userSignupRequestDto.getLoginId()).isEqualTo("로그인아이디입력함");
//        assertThat(userSignupRequestDto.getSecondPw()).isEqualTo("2차비번입력함");
//        assertThat(userSignupRequestDto.getUsername()).isEqualTo("사용자이름입력함");
        //userServiceLogic.save(userSignupRequestDto);
        authService.signup(userSignupRequestDto);
    }

    // @Test
    @DisplayName("사용자 1명 검색_Test")
    @Transactional(readOnly = true)
    void findUser_Test() {  // userId로 검색한 사용자 1명 반환 기능.
        UserResponseDto userResponseDto = userServiceLogic.findById(Long.valueOf(1));  // userId가 1인 사용자 검색
        assertThat(userResponseDto.getId()).isEqualTo(Long.valueOf(1));  // userId가 1이 맞는가?
        assertThat(userResponseDto.getLoginId()).isEqualTo("로그인아이디입력함");  // loginId가 '로그인아이디입력함'이 맞는가?
        assertThat(userResponseDto.getUsername()).isEqualTo("사용자이름입력함");  // username이 '사용자이름입력함'이 맞는가?
    }
}
