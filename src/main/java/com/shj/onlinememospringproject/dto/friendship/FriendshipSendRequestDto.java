package com.shj.onlinememospringproject.dto.friendship;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipSendRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.
    // 신규 친구요청 생성 전용의 RequestDto

    private String loginId;  // 친구요청 받을 사용자의 loginId
    private Long senderUserId;  // 친구요청을 신청하는(보내는) 사용자의 userId

    @Builder
    public FriendshipSendRequestDto(String loginId, Long senderUserId) {
        this.loginId = loginId;
        this.senderUserId = senderUserId;
    }

    // FriendshipSendRequestDto를 클라이언트에게 받아, 그 안의 loginId로 user엔티티를 찾아오고
    // 그걸로 다시 FriendshipRequestDto로 변환하여 사용할것이기때문에,
    // 여기 FriendshipSendRequestDto 클래스 안에 따로 toEntity() 메소드를 만들어줄 필요가 없다.
    // 또한 이러한 이유로, Friendship 클래스 안에 FriendshipSendBuilder와 같은 네임드빌더를 따로 만들어줄 필요도 없다.
}
