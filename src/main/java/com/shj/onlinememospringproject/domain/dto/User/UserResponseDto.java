package com.shj.onlinememospringproject.domain.dto.User;

import com.shj.onlinememospringproject.domain.jpo.Friendship;
import com.shj.onlinememospringproject.domain.jpo.User;
import com.shj.onlinememospringproject.domain.jpo.UserAndMemo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;

@Getter
@NoArgsConstructor
public class UserResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.

    private Long id;
    private String loginId;
    private String firstPw;
    private String secondPw;
    private String username;

    private Set<UserAndMemo> userAndMemos;
    private Set<Friendship> friendships;

    // repository를 통해 조회한 entity를 dto로 변환 용도
    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.loginId = entity.getLoginId();
        this.firstPw = entity.getFirstPw();
        this.secondPw = entity.getSecondPw();
        this.username = entity.getUsername();
    }
}
