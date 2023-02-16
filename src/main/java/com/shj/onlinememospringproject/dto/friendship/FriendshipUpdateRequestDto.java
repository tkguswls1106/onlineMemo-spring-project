package com.shj.onlinememospringproject.dto.friendship;

import com.shj.onlinememospringproject.domain.friendship.Friendship;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipUpdateRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.

    private Integer isFriend;
    private Integer isWait;

    @Builder
    public FriendshipUpdateRequestDto(Integer isFriend, Integer isWait) {
        this.isFriend = isFriend;
        this.isWait = isWait;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Friendship toEntity() {
        return Friendship.FriendshipUpdateBuilder()
                .isFriend(isFriend)
                .isWait(isWait)
                .build();
    }
}
