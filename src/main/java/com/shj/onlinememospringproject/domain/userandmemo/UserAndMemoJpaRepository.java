package com.shj.onlinememospringproject.domain.userandmemo;

import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface UserAndMemoJpaRepository extends JpaRepository<UserAndMemo, Long> {
}
