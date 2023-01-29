package com.shj.onlinememospringproject.domain.dto.Friendship;

import com.shj.onlinememospringproject.domain.jpo.Friendship;
import com.shj.onlinememospringproject.domain.jpo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipResponseDto {

    private User user;

    private Long senderUserId;
    private Integer isFriend;
    private Integer isWait;

    // repository를 통해 조회한 entity를 dto로 변환 용도
    public FriendshipResponseDto(Friendship entity) {
        this.user = User.builder()
                .loginId(entity.getUser().getLoginId())
                .firstPw(entity.getUser().getFirstPw())
                .secondPw(entity.getUser().getSecondPw())
                .username(entity.getUser().getUsername())
                .build();
        this.senderUserId = entity.getSenderUserId();
        this.isFriend = entity.getIsFriend();
        this.isWait = entity.getIsWait();
    }
}
