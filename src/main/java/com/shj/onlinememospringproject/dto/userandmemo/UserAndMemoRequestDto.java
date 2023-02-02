package com.shj.onlinememospringproject.dto.userandmemo;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAndMemoRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.

    private User user;
    private Memo memo;

    @Builder
    public UserAndMemoRequestDto(User user, Memo memo) {
        this.user = user;
        this.memo = memo;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public UserAndMemo toEntity(User user, Memo memo) {
        return UserAndMemo.builder()
                .user(user)
                .memo(memo)
                .build();
    }
}
