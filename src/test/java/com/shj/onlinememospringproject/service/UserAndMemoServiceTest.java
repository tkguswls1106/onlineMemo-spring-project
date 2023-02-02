package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.service.logic.MemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserAndMemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserAndMemoServiceTest {

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
    @DisplayName("사용자의 메모들 검색_Test")
    void userFindMemos_Test() {  // userId와 일치하는 사용자의 메모들 리스트 반환 기능.
        assertThat(userAndMemoServiceLogic.findMemosByUserId(Long.valueOf(3)).size()).isEqualTo(2);
        // userId가 3인 사용자의 메모들의 개수가 2개가 맞는가?
    }

    @Test
    @DisplayName("메모의 사용자들 검색_Test")
    void memoFindUsers_Test() {  //
        assertThat(userAndMemoServiceLogic.findUsersByMemoId(Long.valueOf(8)).size()).isEqualTo(2);
        // memoId가 8인 메모의 사용자들의 수가 2명이 맞는가?
    }

    @Test
    @DisplayName("메모에 사용자 초대_Test")
    void inviteUser_Test() {
        List<UserResponseDto> userResponseDtos = userAndMemoServiceLogic.inviteUserToMemo(
                Long.valueOf(3),  // userId가 3인 친구(사용자)를
                Long.valueOf(5)  // memoId가 5인 메모에 초대
                // 했을때 해당 메모의 사용자는 몇명?
        );

        int countUsers = userResponseDtos.size();  // 사용자 몇명인가

        assertThat(countUsers).isEqualTo(2);  // 해당 메모의 현재 사용자가 2명이 맞는가?
    }
}
