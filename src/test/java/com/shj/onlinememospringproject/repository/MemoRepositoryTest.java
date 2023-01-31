package com.shj.onlinememospringproject.repository;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    MemoJpaRepository memoJpaRepository;

    @Test
    void save() {
        Memo memo = Memo.builder()
                .title("메모 제목")
                .content("메모 내용")
                .build();

        memoJpaRepository.save(memo);

        Memo entity = memoJpaRepository.findById((long) 1).get();
        assertThat(entity.getTitle()).isEqualTo("메모 제목");
        assertThat(entity.getContent()).isEqualTo("메모 내용");
    }
}
