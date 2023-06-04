package com.shj.onlinememospringproject.domain.userandmemo;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface UserAndMemoJpaRepository extends JpaRepository<UserAndMemo, Long> {

    boolean existsByUserAndMemo(User user, Memo memo);  // 동일한 사용자와 동일한 메모 정보를 가진 튜플 데이터가 이미 DB에 존재하는지 반환.
    void deleteAllByUser(User user);  // 해당 사용자에 대한 메모와의 관계 모두 삭제.
    void deleteByUserAndMemo(User user, Memo memo);  // 해당 사용자와 해당 메모에 대한 사용자와메모 관계 삭제.
}
