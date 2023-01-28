package com.shj.onlinememospringproject.domain.dto;

import com.shj.onlinememospringproject.domain.jpo.Memo;

import javax.validation.constraints.NotBlank;

public class MemoDto {

    // @NotBlank(message = "!!! 제목은 비워둘수 없습니다. !!!")
    String title;

    // @NotBlank(message = "!!! 내용은 비워둘수 없습니다. !!!")
    String content;


    public Memo toEntity() {
        return Memo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
