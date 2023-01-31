package com.shj.onlinememospringproject.domain.dto.User;

import com.shj.onlinememospringproject.domain.jpo.Friendship;
import com.shj.onlinememospringproject.domain.jpo.User;
import com.shj.onlinememospringproject.domain.jpo.UserAndMemo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.
    // 일반적인 용도의, 사용자 정보를 가져오는 ResponseDto

    private Long id;
    private String loginId;
    private String username;

    // repository를 통해 조회한 entity를 dto로 변환 용도
    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.loginId = entity.getLoginId();
        this.username = entity.getUsername();
    }
}
