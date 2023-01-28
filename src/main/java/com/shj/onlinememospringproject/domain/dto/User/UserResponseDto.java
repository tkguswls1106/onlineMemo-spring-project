package com.shj.onlinememospringproject.domain.dto.User;

import com.shj.onlinememospringproject.domain.jpo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String loginId;
    private String firstPw;
    private String secondPw;
    private String username;

    // repository를 통해 조회한 entity를 dto로 변환 용도
    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.loginId = entity.getLoginId();
        this.firstPw = entity.getFirstPw();
        this.secondPw = entity.getSecondPw();
        this.username = entity.getUsername();
    }
}
