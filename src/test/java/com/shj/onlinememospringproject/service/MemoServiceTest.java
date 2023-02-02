package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.service.logic.MemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserAndMemoServiceLogic;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemoServiceTest {

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
    @DisplayName("메모 저장_Test")
    void memoSave_Test() {
        String title = "메모제목입력";
        String content = "메모내용입력";

        MemoRequestDto memoRequestDto = MemoRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        memoServiceLogic.saveMemo(Long.valueOf(3), memoRequestDto);  // userId가 3인 사용자의 메모 생성
    }

    @Test
    @DisplayName("메모 1개 검색_Test")
    void findMemo_Test() {  // memoId로 검색한 메모 1개 반환 기능.
        MemoResponseDto memoResponseDto = memoServiceLogic.findById(Long.valueOf(6));  // memoId가 6인 메모 검색
        assertThat(memoResponseDto.getId()).isEqualTo(Long.valueOf(6));  // memoId가 6이 맞는가?
        assertThat(memoResponseDto.getTitle()).isEqualTo("가나다");  // title가 '가나다'가 맞는가?
        assertThat(memoResponseDto.getContent()).isEqualTo("사현진");  // content가 '사현진'이 맞는가?
    }
}
