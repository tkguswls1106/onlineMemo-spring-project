package com.shj.onlinememospringproject.dto.friendship;

import com.shj.onlinememospringproject.domain.friendship.Friendship;
import com.shj.onlinememospringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.

    private User user;
    private Long senderUserId;

    @Builder
    public FriendshipRequestDto(User user, Long senderUserId) {
        this.user = user;
        this.senderUserId = senderUserId;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Friendship toEntity(User user) {
        return Friendship.builder()
                .user(user)
                .senderUserId(senderUserId)
                .build();
    }
}
