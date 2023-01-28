package com.shj.onlinememospringproject.domain.jpo;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor

@Table(name = "user")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @Column(name = "first_password", nullable = false)
    private String firstPw;

    @Column(name = "second_password", nullable = false)
    private String secondPw;

    @Column(name = "username", nullable = false)
    private String username;

    // 참고로 이건 db에 안나타남.
    @OneToMany(mappedBy = "user")
    private Set<UserAndMemo> userAndMemos = new HashSet<>();  // 나중에 안되면 Set말고 List로 변경하자!

    // 참고로 이건 db에 안나타남.
    @OneToMany(mappedBy = "user")
    private Set<Friendship> friendships = new HashSet<>();  // 나중에 안되면 Set말고 List로 변경하자!


    @Builder
    public User(String loginId, String firstPw, String secondPw, String username) {
        this.loginId = loginId;
        this.firstPw = firstPw;
        this.secondPw = secondPw;
        this.username = username;
    }
}