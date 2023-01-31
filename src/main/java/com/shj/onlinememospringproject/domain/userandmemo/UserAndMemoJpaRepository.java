package com.shj.onlinememospringproject.domain.userandmemo;

import org.springframework.data.jpa.repository.JpaRepository;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface UserAndMemoJpaRepository extends JpaRepository<UserAndMemo, Long> {
}
