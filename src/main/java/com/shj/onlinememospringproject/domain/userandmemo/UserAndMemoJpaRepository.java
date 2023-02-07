package com.shj.onlinememospringproject.domain.userandmemo;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface UserAndMemoJpaRepository extends JpaRepository<UserAndMemo, Long> {

    boolean existsByUserAndMemo(User user, Memo memo);  // 동일한 사용자와 동일한 메모 정보를 가진 컬럼이 이미 DB에 존재하는지 반환.
    void deleteAllByUser(User user);  // 해당 사용자에 대한 메모와의 관계 모두 삭제.
}
