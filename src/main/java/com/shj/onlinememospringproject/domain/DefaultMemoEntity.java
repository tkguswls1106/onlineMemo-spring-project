package com.shj.onlinememospringproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
    protected String modifiedDate;

//    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
//    public void onPrePersist(){
//        this.createdDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//        this.modifiedDate = this.createdDate;
//    }

    @Column(name = "is_star", columnDefinition = "TINYINT(1) default 0", length = 1)
    protected Integer isStar;


    // 아마 이건 안쓰일듯하지만 혹여 사용처가 생길지도 몰라 ResponseDto의 메소드와 똑같이 그대로 적어주었다.
    @JsonIgnore
    public LocalDateTime getDateTimeModifiedDate() {  // 날짜 정렬에 사용할 string형식의 날짜를 localDateTime형식으로 변환하는 메소드이다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm");
        LocalDateTime dateTimeModifiedDate = LocalDateTime.parse(this.modifiedDate, formatter);
        return dateTimeModifiedDate;
    }


    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
    public void onPrePersist(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm").withLocale(Locale.forLanguageTag("ko")));
        this.isStar = this.isStar == null ? 0 : this.isStar;
    }

    @PreUpdate  // 해당 엔티티를 업데이트 하기 이전에 실행된다.
    // 참고로 엔티티 컬럼중에서 isStar이 업데이트 될때의 경우는 제외하고 modifiedDate을 @PreUpdate을 적용시켜야만 한다.
    // 그렇기에 @EnableJpaAuditing인 JPA Entity LifeCycle 에서 벗어난 isStar의 update기능을 구현하기 위하여 따로 MemoJpaRepository 인터페이스에 @Query로 updateStar메소드를 따로 작성해주었다.
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm").withLocale(Locale.forLanguageTag("ko")));
    }
}