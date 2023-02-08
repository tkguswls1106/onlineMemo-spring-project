package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoSaveRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
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
    // @Transactional
    void memoSave_Test() {  // 신규 메모 생성하고 memoId 반환 기능.
        Long userId = Long.valueOf(1);
        String title = "메모제목입력";
        String content = "메모내용입력";

        MemoSaveRequestDto memoSaveRequestDto = MemoSaveRequestDto.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .build();

        memoServiceLogic.saveMemo(memoSaveRequestDto);  // userId가 1인 사용자의 메모 생성
    }

    @Test
    @DisplayName("메모 1개 검색_Test")
    @Transactional(readOnly = true)
    void findMemo_Test() {  // memoId로 검색한 메모 1개 반환 기능.
        MemoResponseDto memoResponseDto = memoServiceLogic.findById(Long.valueOf(1));  // memoId가 1인 메모 검색
        assertThat(memoResponseDto.getId()).isEqualTo(Long.valueOf(1));  // memoId가 1이 맞는가?
        assertThat(memoResponseDto.getTitle()).isEqualTo("메모제목입력");  // title이 '메모제목입력'가 맞는가?
        assertThat(memoResponseDto.getContent()).isEqualTo("메모내용입력");  // content가 '메모내용입력'이 맞는가?
    }
}
