package com.shj.onlinememospringproject.domain.dto.Memo;

import com.shj.onlinememospringproject.domain.jpo.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoRequestDto {  // 요청하는 DTO. 예를들어 CRUD의 C. method로는 post.

    private String title;
    private String content;

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Memo toEntity() {
        return Memo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
