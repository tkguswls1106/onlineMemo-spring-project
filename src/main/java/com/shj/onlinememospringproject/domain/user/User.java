package com.shj.onlinememospringproject.domain.user;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "user")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", unique = true)
    private String loginId;

    @Column(name = "first_password")
    private String firstPw;

    @Column(name = "second_password")
    private String secondPw;

    @Column(name = "username")
    private String username;


    @Builder
    public User(Long id, String loginId, String username) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
    }

    @Builder(builderClassName = "UserJoinBuilder", builderMethodName = "UserJoinBuilder")
    public User(String loginId, String firstPw, String secondPw, String username) {
        // 이 빌더는 사용자 회원가입때만 사용할 용도
        this.loginId = loginId;
        this.firstPw = firstPw;
        this.secondPw = secondPw;
        this.username = username;
    }

    @Builder(builderClassName = "UserUpdatePwBuilder", builderMethodName = "UserUpdatePwBuilder")
    public User(String loginId, String newFirstPw, String secondPw) {
        // 이 빌더는 사용자 1차비밀번호 수정때만 사용할 용도
        this.loginId = loginId;
        this.firstPw = newFirstPw;
        this.secondPw = secondPw;
    }

    @Builder(builderClassName = "UserUpdateNameBuilder", builderMethodName = "UserUpdateNameBuilder")
    public User(String username) {
        // 이 빌더는 사용자 이름 수정때만 사용할 용도
        this.username = username;
    }


    // 수정(업데이트) 기능
    public void updateFirstPw(String newFirstPw) {  // 1차 패스워드 변경 기능
        this.firstPw = newFirstPw;
    }
    public void updateUsername(String username) {  // 사용자이름 변경 기능
        this.username = username;
    }
}