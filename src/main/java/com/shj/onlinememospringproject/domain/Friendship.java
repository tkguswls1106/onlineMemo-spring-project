package com.shj.onlinememospringproject.domain;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Data

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Table(name = "friendship")
@Entity
public class Friendship implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")  // 기준임. 친구요청 받은 유저.
    private User user;

    // 나중에 반드시 같은 user_id 에 대하여 같은 sender_user_id 가 안나오도록 조건문으로 중복안되게 막도록하자!
    //@NonNull
    @Column(name = "sender_user_id")  // 친구요청 보낸 유저.
    private Long senderUserId;

    //@NonNull
    @Column(name = "is_friend", columnDefinition = "TINYINT(1) DEFAULT 0", length = 1, nullable = false)
    private boolean isFriend;

    //@NonNull
    @Column(name = "is_wait", columnDefinition = "TINYINT(1) DEFAULT 1", length = 1, nullable = false)
    private boolean isWait;
}