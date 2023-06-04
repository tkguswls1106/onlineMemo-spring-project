package com.shj.onlinememospringproject.dto.memo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemoResponseDto {  // 요청받아 가져오는 DTO. 예를들어 CRUD의 R. method로는 get.

    private Long id;
    private String title;
    private String content;
    private String modifiedDate;
    private Integer isStar;

    private List<UserResponseDto> userResponseDtos;  // 해당 메모를 가지고 있는 사용자들의 리스트
    private Integer memoHasUsersCount;  // 해당 메모를 가지고 있는 사용자의 수를 카운트.

    public void setUserResponseDtos(List<UserResponseDto> userResponseDtos) {
        this.userResponseDtos = userResponseDtos;
    }
    public void setMemoHasUsersCount(List<UserResponseDto> userResponseDtos) {
        this.memoHasUsersCount = userResponseDtos.size();
    }

    @JsonIgnore
    public LocalDateTime getDateTimeModifiedDate() {  // 날짜 정렬에 사용할 string형식의 날짜를 localDateTime형식으로 변환하는 메소드이다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm");
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
