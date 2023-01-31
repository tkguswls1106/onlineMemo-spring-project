package com.shj.onlinememospringproject.dto.friendship;

import com.shj.onlinememospringproject.domain.friendship.Friendship;
import com.shj.onlinememospringproject.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.

    private Long userId;
    // 이는 특이하게도, user객체를 받지않고, 데이터로 userId를 입력받아와서 서비스 레이어에서 해당 userId에 해당되는 user 객체를 toEntity메소드에 넣어줄수있도록 로직을 설계하였다.
    // 이를 직접 서비스 레이어에다가, userId를 입력받아 해당 id와 일치하는 user객체를 찾는 find로직을 추가적으로 구현해야함을 잊지말자.
    private Long senderUserId;

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Friendship toEntity(User user) {
        return Friendship.builder()
                .user(user)
                .senderUserId(senderUserId)
                .build();
    }
}
