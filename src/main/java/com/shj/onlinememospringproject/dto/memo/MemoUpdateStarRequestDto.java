package com.shj.onlinememospringproject.dto.memo;

import com.shj.onlinememospringproject.domain.memo.Memo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoUpdateStarRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.
    // 메모 즐겨찾기 여부 수정 전용의 RequestDto

    private Integer isStar;

    @Builder
    public MemoUpdateStarRequestDto(Integer isStar) {
        this.isStar = isStar;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Memo toEntity() {
        return Memo.MemoUpdateStarBuilder()
                .isStar(isStar)
                .build();
    }
}
