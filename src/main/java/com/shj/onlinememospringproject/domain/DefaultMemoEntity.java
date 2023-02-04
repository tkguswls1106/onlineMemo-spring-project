package com.shj.onlinememospringproject.domain;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DefaultMemoEntity {

//    @Column(name = "created_date")
//    @CreatedDate
//    private String createdDate;

    // 시간 업데이트 자동화시킴.
    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;

//    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
//    public void onPrePersist(){
//        this.createdDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//        this.modifiedDate = this.createdDate;
//    }

    @Column(name = "is_star", columnDefinition = "TINYINT(1) default 0", length = 1)
    protected Integer isStar;


    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
    public void onPrePersist(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm").withLocale(Locale.forLanguageTag("ko")));
        this.isStar = this.isStar == null ? 0 : this.isStar;
    }

    @PreUpdate  // 해당 엔티티를 업데이트 하기 이전에 실행된다.
    // 참고로 이걸 엔티티 컬럼중에서 isStar이 업데이트 될때의 경우는 제외하고 modifiedDate을 @PreUpdate을 적용시켜야만 한다.
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm").withLocale(Locale.forLanguageTag("ko")));
    }
}