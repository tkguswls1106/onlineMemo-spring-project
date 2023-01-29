package com.shj.onlinememospringproject.domain.dto.User;

import com.shj.onlinememospringproject.domain.jpo.Friendship;
import com.shj.onlinememospringproject.domain.jpo.User;
import com.shj.onlinememospringproject.domain.jpo.UserAndMemo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;

@Getter
@NoArgsConstructor
public class UserResponseDto {

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
