package com.shj.onlinememospringproject.domain.dto.Memo;

import com.shj.onlinememospringproject.domain.jpo.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoRequestDto {

    private String title;
    private String content;

    // 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public Memo toEntity() {
        return Memo.builder()
                .title(title)
                .content(content)
                .isStar(0)
                .build();
    }
}
