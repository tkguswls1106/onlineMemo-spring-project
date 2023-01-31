package com.shj.onlinememospringproject.domain.friendship;

import org.springframework.data.jpa.repository.JpaRepository;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface FriendshipJpaRepository extends JpaRepository<Friendship, Long> {
}
