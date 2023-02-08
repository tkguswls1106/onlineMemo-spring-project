package com.shj.onlinememospringproject.domain.memo;

import com.shj.onlinememospringproject.domain.DefaultMemoEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "memo")
@Entity
public class Memo extends DefaultMemoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;


    @Builder
    public Memo(Long id, String title, String content, String modifiedDate, Integer isStar) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifiedDate = modifiedDate;
        this.isStar = isStar;
    }

    @Builder(builderClassName = "MemoUpdateBuilder", builderMethodName = "MemoUpdateBuilder")
    public Memo(String title, String content) {
        // 이 빌더는 메모 수정때만 사용할 용도
        this.title = title;
        this.content = content;
    }

    @Builder(builderClassName = "MemoUpdateStarBuilder", builderMethodName = "MemoUpdateStarBuilder")
    public Memo(Integer isStar) {
        // 이 빌더는 메모 즐겨찾기 여부 수정때만 사용할 용도
        this.isStar = isStar;
    }


    // 수정(업데이트) 기능
    public void updateMemo(String title, String content) {  // 메모 수정 기능.
        this.title = title;
        this.content = content;
    }

    // 이는 따로 MemoJpaRepository 인터페이스에 @Query로 updateStar메소드를 따로 작성해주었다. 그 이유는 DefaultMemoEntity 클래스에 주석으로 첨부하였음.
//    public void updateStar(Integer isStar) {  // 메모 즐겨찾기 여부 변경 기능.
//        this.isStar = isStar;
//    }
}