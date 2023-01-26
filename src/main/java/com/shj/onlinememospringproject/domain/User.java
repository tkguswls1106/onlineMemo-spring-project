package com.shj.onlinememospringproject.domain;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Table(name = "user")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //@NonNull
    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    //@NonNull
    @Column(name = "first_password", nullable = false)
    private String firstPw;

    //@NonNull
    @Column(name = "second_password", nullable = false)
    private String secondPw;

    //@NonNull
    @Column(name = "username", nullable = false)
    private String username;

    // 참고로 이건 db에 안나타남.
    @OneToMany(mappedBy = "user")
    private Set<UserAndMemo> userAndMemos = new HashSet<>();  // 나중에 안되면 Set말고 List로 변경하자!

    // 참고로 이건 db에 안나타남.
    @OneToMany(mappedBy = "user")
    private Set<Friendship> friendships = new HashSet<>();  // 나중에 안되면 Set말고 List로 변경하자!
}