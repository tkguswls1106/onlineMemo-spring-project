package com.shj.onlinememospringproject.dto.user;

import com.shj.onlinememospringproject.domain.user.Authority;
import com.shj.onlinememospringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    private String loginId;
    private String firstPw;

    @Builder
    public UserLoginRequestDto(String loginId, String firstPw) {
        this.loginId = loginId;
        this.firstPw = firstPw;
    }


    // UsernamePasswordAuthenticationToken을 반환하여 아이디와 비밀번호가 일치하는지 검증하는 로직을 넣을 수 있게된다.
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, firstPw);
    }
}
