package com.shj.onlinememospringproject.domain.friendship;

import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.dto.friendship.FriendshipResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface FriendshipJpaRepository extends JpaRepository<Friendship, Long> {

    boolean existsByUserAndSenderUserId(User user, Long senderUserId);  // 동일한 사용자와 senderUserId를 가진 컬럼이 이미 DB에 존재하는지 반환.
    List<Friendship> findAllByUser(User user);  // 해당 사용자의 모든 친구관계 반환.
}
