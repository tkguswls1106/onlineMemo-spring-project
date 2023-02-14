package com.shj.onlinememospringproject.domain.friendship;

import com.shj.onlinememospringproject.domain.DefaultFriendshipEntity;
import com.shj.onlinememospringproject.domain.user.User;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "friendship")
@Entity
public class Friendship extends DefaultFriendshipEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")  // 친구요청 받은 유저.
    private User user;

    // 나중에 반드시 같은 user_id 에 대하여 같은 sender_user_id 가 안나오도록 조건문으로 중복안되게 막도록하자!
    @Column(name = "sender_user_id")  // 친구요청 보낸 유저.
    private Long senderUserId;


    @Builder
    public Friendship(User user, Long senderUserId) {
        this.user = user;
        this.senderUserId = senderUserId;
    }

    @Builder(builderClassName = "FriendshipUpdateBuilder", builderMethodName = "FriendshipUpdateBuilder")
    public Friendship(Long senderUserId, Integer isFriend, Integer isWait) {
        // 이 빌더는 친구요청 관계 수정때만 사용할 용도
        this.senderUserId = senderUserId;

        this.isFriend = isFriend;
        this.isWait = isWait;
    }


    // 초기 친구요청 상태 => isFriend:0, isWait:1
    // 친구요청 수락 상태 => isFriend:1, isWait:0 => 친구 맺기 성공
    // 친구요청 거절 상태 => isFriend:0, isWait:0 => 친구 맺기 실패 => Friendship DB 테이블에서 해당 컬럼 삭제 조치.
    // isFriend:1, isWait:1 => 에러임. 예외처리 조치.

    // 수정(업데이트) 기능
    // 이는 따로 FriendshipJpaRepository 인터페이스에 @Query로 updateFriendship메소드를 따로 작성해주었다. 그 이유는 FriendshipJpaRepository 인터페이스에 주석으로 첨부하였음.
//    public void updateFriendship(Integer isFriend, Integer isWait) {  // 친구요청 수락시 업데이트 기능
//        this.isFriend = isFriend;
//        this.isWait = isWait;
//    }
}