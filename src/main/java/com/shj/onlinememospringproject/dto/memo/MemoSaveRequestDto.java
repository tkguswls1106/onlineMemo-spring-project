package com.shj.onlinememospringproject.dto.memo;

import com.shj.onlinememospringproject.domain.memo.Memo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoSaveRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.
    // 신규 개인메모 생성 전용의 RequestDto

    private Long userId;  // 메모작성할 사용자의 userId

    private String title;  // 메모 작성 제목
    private String content;  // 메모 작성 내용

    @Builder
    public MemoSaveRequestDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Memo toEntity() {
        return Memo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
