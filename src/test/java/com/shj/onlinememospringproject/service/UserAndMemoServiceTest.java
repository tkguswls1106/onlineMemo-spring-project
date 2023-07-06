package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.service.logic.MemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserAndMemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 사실 여기에 @Transactional 선언해도된다.
// 만약 롤백을 하지않고싶은 메소드가 따로 있다면, 헤당 테스트메소드에 각각 @Rollback(false)을 적어주면된다.
// @SpringBootTest
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


    // @Test
    @DisplayName("사용자의 메모들 검색_Test")
    @Transactional(readOnly = true)
    void userFindMemos_Test() {  // userId와 일치하는 사용자의 메모들 리스트를 정렬후 반환 기능.
        assertThat(userAndMemoServiceLogic.findMemosByUserId(Long.valueOf(1)).size()).isEqualTo(3);
        // userId가 1인 사용자의 메모들의 개수가 3개가 맞는가?
    }

//    @Test
//    @DisplayName("메모에 사용자 초대_Test")
//        // @Transactional
//    void inviteUser_Test() {  // memoId와 userId를 받아 특정 메모 1개에 친구(사용자) 1명을 메모에 초대하고 모든 공동 사용자들 리스트 반환 기능.
//        List<UserResponseDto> userResponseDtos = userAndMemoServiceLogic.inviteUserToMemo(
//                Long.valueOf(3),  // userId가 3인 친구(사용자)를
//                Long.valueOf(2)  // memoId가 2인 메모에 초대
//                // 했을때 해당 메모의 사용자는 몇명?
//        );
//
//        int countUsers = userResponseDtos.size();  // 사용자 몇명인가
//
//        assertThat(countUsers).isEqualTo(2);  // 해당 메모의 현재 사용자가 2명이 맞는가?
//    }

    // @Test
    @DisplayName("메모의 사용자들 검색_Test")
    @Transactional(readOnly = true)
    void memoFindUsers_Test() {  // memoId와 일치하는 메모의 사용자들 리스트를 정렬후 반환 기능.
        assertThat(userAndMemoServiceLogic.findUsersByMemoId(Long.valueOf(2)).size()).isEqualTo(3);
        // memoId가 2인 메모의 사용자들의 수가 3명이 맞는가?
    }
}
