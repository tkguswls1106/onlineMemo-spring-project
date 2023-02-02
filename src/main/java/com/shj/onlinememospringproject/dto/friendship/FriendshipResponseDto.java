package com.shj.onlinememospringproject.dto.friendship;

import com.shj.onlinememospringproject.domain.friendship.Friendship;
import com.shj.onlinememospringproject.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.

    private Long id;

    private User user;

    private Long senderUserId;
    private Integer isFriend;
    private Integer isWait;

    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public FriendshipResponseDto(Friendship entity) {
        this.id = entity.getId();
        this.user = User.builder()
                .id(entity.getUser().getId())
                .loginId(entity.getUser().getLoginId())
                .username(entity.getUser().getUsername())
                .build();
        this.senderUserId = entity.getSenderUserId();
        this.isFriend = entity.getIsFriend();
        this.isWait = entity.getIsWait();
    }
}
