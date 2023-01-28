package com.shj.onlinememospringproject.domain.dto;

import com.shj.onlinememospringproject.domain.jpo.User;

import javax.validation.constraints.NotBlank;

public class UserDto {

    // @NotBlank(message = "!!! 로그인id는 비워둘수 없습니다. !!!")
    String loginId;

    // @NotBlank(message = "!!! 1차비번은 비워둘수 없습니다. !!!")
    String firstPw;

    // @NotBlank(message = "!!! 2차비번은 비워둘수 없습니다. !!!")
    String secondPw;

    // @NotBlank(message = "!!! 사용자이름은 비워둘수 없습니다. !!!")
    String username;


    public User toEntity() {
        return User.builder()
                .loginId(loginId)
                .firstPw(firstPw)
                .secondPw(secondPw)
                .username(username)
                .build();
    }
}
