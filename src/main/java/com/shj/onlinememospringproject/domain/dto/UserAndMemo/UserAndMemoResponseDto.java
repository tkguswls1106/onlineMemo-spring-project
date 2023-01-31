package com.shj.onlinememospringproject.domain.dto.UserAndMemo;

import com.shj.onlinememospringproject.domain.jpo.Memo;
import com.shj.onlinememospringproject.domain.jpo.User;
import com.shj.onlinememospringproject.domain.jpo.UserAndMemo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAndMemoResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.

    private Long id;

    private User user;
    private Memo memo;

    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public UserAndMemoResponseDto(UserAndMemo entity) {
        this.id = entity.getId();
        this.user = User.builder()
                .id(entity.getUser().getId())
                .loginId(entity.getUser().getLoginId())
                .username(entity.getUser().getUsername())
                .build();
        this.memo = Memo.builder()
                .title(entity.getMemo().getTitle())
                .content(entity.getMemo().getContent())
                .build();
    }
}
