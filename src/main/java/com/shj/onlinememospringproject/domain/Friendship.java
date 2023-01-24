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
@IdClass(Friendship.class)
public class Friendship implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pk_fk_called_userid")  // 기준임. 친구요청받은 유저.
    private User user;

    // 나중에 반드시 같은 pk_fk_called_userid 에 대하여 같은 pk_calling_userid 가 안나오도록 조건문으로 중복안되게 막도록하자!
    //@NonNull
    @Column(name = "pk_calling_userid", nullable = false)  // 친구요청한 유저.
    private Long callingUserid;

    //@NonNull
    @Column(name = "is_friend", columnDefinition = "TINYINT(1) DEFAULT 0", length = 1, nullable = false)
    private boolean isFriend;

    //@NonNull
    @Column(name = "is_wait", columnDefinition = "TINYINT(1) DEFAULT 1", length = 1, nullable = false)
    private boolean isWait;
}