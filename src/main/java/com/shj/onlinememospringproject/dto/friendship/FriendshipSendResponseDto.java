package com.shj.onlinememospringproject.dto.friendship;

import com.shj.onlinememospringproject.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendshipSendResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.
    // 신규 친구요청 보낸후 프론트엔드에 전달할 json 데이터 생성 전용 ResponseDto

    private Long senderUserId;  // 친구요청 보낸 사용자

    private Long userId;  // 친구요청 받는 사용자

    private Integer isFriend;
    private Integer isWait;

    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public FriendshipSendResponseDto(Long senderUserId, User entity) {
        this.senderUserId = senderUserId;

        this.userId = entity.getId();

        this.isFriend = 0;
        this.isWait = 1;
    }
}
