package com.shj.onlinememospringproject.domain.jpo;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor

@Table(name = "memo")
@Entity
public class Memo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Builder.Default
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "is_star", columnDefinition = "TINYINT(1)", length = 1)
    private Integer isStar = 0;

    // 참고로 이건 db에 안나타남.
    @Builder.Default
    @OneToMany(mappedBy = "memo")
    private List<UserAndMemo> userAndMemos = new ArrayList<>();


    @Builder
    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }


    // 수정(업데이트) 기능
    public void updateMemo(String title, String content) {  // 메모 수정 기능.
        this.title = title;
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
    }
    public void updateStar(Integer isStar) {  // 메모 즐겨찾기 여부 변경 기능.
        this.isStar = isStar;
    }
}