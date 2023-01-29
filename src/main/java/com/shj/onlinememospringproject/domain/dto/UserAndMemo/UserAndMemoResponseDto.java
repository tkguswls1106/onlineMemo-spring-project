package com.shj.onlinememospringproject.domain.dto.UserAndMemo;

import com.shj.onlinememospringproject.domain.jpo.Memo;
import com.shj.onlinememospringproject.domain.jpo.User;
import com.shj.onlinememospringproject.domain.jpo.UserAndMemo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAndMemoResponseDto {

    private Long id;

    private User user;
    private Memo memo;

    // repository를 통해 조회한 entity를 dto로 변환 용도
    public UserAndMemoResponseDto(UserAndMemo entity) {
        this.id = entity.getId();
        this.user = User.builder()
                .loginId(entity.getUser().getLoginId())
                .firstPw(entity.getUser().getFirstPw())
                .secondPw(entity.getUser().getSecondPw())
                .username(entity.getUser().getUsername())
                .build();
        this.memo = Memo.builder()
                .title(entity.getMemo().getTitle())
                .content(entity.getMemo().getContent())
                .isStar(entity.getMemo().getIsStar())
                .build();
    }
}
