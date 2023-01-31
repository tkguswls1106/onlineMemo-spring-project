package com.shj.onlinememospringproject.domain.dto.Friendship;

import com.shj.onlinememospringproject.domain.jpo.Friendship;
import com.shj.onlinememospringproject.domain.jpo.User;
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

    // repository를 통해 조회한 entity를 dto로 변환 용도
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
