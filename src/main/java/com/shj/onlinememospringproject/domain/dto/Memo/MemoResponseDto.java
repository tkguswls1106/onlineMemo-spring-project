package com.shj.onlinememospringproject.domain.dto.Memo;

import com.shj.onlinememospringproject.domain.jpo.Memo;
import com.shj.onlinememospringproject.domain.jpo.UserAndMemo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
public class MemoResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer isStar;

    private Set<UserAndMemo> userAndMemos;

    // repository를 통해 조회한 entity를 dto로 변환 용도
    public MemoResponseDto(Memo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.isStar = entity.getIsStar();
    }
}
