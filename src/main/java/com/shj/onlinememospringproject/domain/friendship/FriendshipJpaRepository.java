package com.shj.onlinememospringproject.domain.friendship;

import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.dto.friendship.FriendshipResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface FriendshipJpaRepository extends JpaRepository<Friendship, Long> {

    boolean existsByUserAndSenderUserId(User user, Long senderUserId);  // 이에 해당되는 사용자와 senderUserId를 가진 튜플 데이터가 이미 DB에 존재하는지 반환.
    List<Friendship> findAllByUser(User user);  // 해당 사용자의 모든 친구관계 반환.
    Optional<Friendship> findByUserAndSenderUserId(User user, Long senderUserId);  // 이에 해당되는 사용자와 senderUserId를 가진 튜플 데이터를 반환.

    @Modifying(clearAutomatically = true)
    @Query(value = "update friendship f set f.is_friend = :friend, f.is_wait = :wait where f.friendship_id = :fid", nativeQuery = true)
    void updateFriendship(@Param("fid") Long id, @Param("friend") Integer isFriend, @Param("wait") Integer isWait);  // 친구관계 수정 기능.
    // 이는 더티체킹으로 인한 업데이트를 막아 JPA 영속성 생명주기에서 벗어나 바로 DB에 반영시키고 바로 flush & clear을 진행해주어,
    // 원하는 순서대로 save와 update 순서를 지정하여 실행할 수 있게 하기위해 따로 쿼리메소드로 작성해준것이다.
    // 만약 쿼리메소드와 clear로 영속성 1차 캐시를 비워주지않고 더티체킹을 사용하여 트랜잭션 안에서 JPA 영속성으로 save와 update 를 실행했다면,
    // 이는 insert 진행후 모두 종료된 후 커밋된 후에 이제서야 update가 반영되어, 여러 update와 save 메소드 사용시 뒤죽박죽 순서가 되어 원하는바대로 코드가 실행되지 않을 수 있다.
}
