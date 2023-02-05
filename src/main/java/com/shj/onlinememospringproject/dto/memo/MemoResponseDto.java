package com.shj.onlinememospringproject.dto.memo;

import com.shj.onlinememospringproject.domain.memo.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class MemoResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.

    private Long id;
    private String title;
    private String content;
    private String modifiedDate;
    private Integer isStar;

    public LocalDateTime getDateTimeModifiedDate() {  // 날짜 정렬에 사용할 string형식의 날짜를 localDateTime형식으로 변환하는 메소드이다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyy. M. d. a h:mm");
        LocalDateTime dateTimeModifiedDate = LocalDateTime.parse(this.modifiedDate, formatter);
        return dateTimeModifiedDate;
    }

    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public MemoResponseDto(Memo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.modifiedDate = entity.getModifiedDate();
        this.isStar = entity.getIsStar();
    }
}
