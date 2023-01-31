package com.shj.onlinememospringproject.domain.jpo;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "friendship")
@Entity
public class Friendship implements Serializable {

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

    @Builder.Default
    @Column(name = "is_friend", columnDefinition = "TINYINT(1)", length = 1)
    private Integer isFriend = 0;

    @Builder.Default
    @Column(name = "is_wait", columnDefinition = "TINYINT(1)", length = 1)
    private Integer isWait = 1;


    @Builder
    public Friendship(User user, Long senderUserId) {
        this.user = user;
        this.senderUserId = senderUserId;
    }


    // 수정(업데이트) 기능
    public void updateYes() {  // 친구요청 수락시 업데이트 기능
        this.isFriend = 1;
        this.isWait = 0;
    }
    public void updateNo() {  // 친구요청 거절시 업데이트 기능
        this.isFriend = 0;
        this.isWait = 0;
    }
}