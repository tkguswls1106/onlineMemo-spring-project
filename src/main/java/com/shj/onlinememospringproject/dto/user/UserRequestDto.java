package com.shj.onlinememospringproject.dto.user;

import com.shj.onlinememospringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.
    // 일반적인 용도의, 사용자 정보를 전달해주는 RequestDto

    private String loginId;
    private String username;

    @Builder
    public UserRequestDto(String loginId, String username) {
        this.loginId = loginId;
        this.username = username;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public User toEntity() {
        return User.builder()
                .loginId(loginId)
                .username(username)
                .build();
    }
}
