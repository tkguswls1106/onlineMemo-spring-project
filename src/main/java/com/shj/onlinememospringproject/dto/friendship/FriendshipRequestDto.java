package com.shj.onlinememospringproject.dto.friendship;

import com.shj.onlinememospringproject.domain.friendship.Friendship;
import com.shj.onlinememospringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.
    // 일반적인 용도의, 사용자 정보를 전달해주는 RequestDto

    private User user;  // 친구요청 받을 사용자
    private Long senderUserId;  // 친구요청을 신청하는(보내는) 사용자의 userId

    @Builder
    public FriendshipRequestDto(User user, Long senderUserId) {
        this.user = user;
        this.senderUserId = senderUserId;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Friendship toEntity() {
        return Friendship.builder()
                .user(user)
                .senderUserId(senderUserId)
                .build();
    }
}
