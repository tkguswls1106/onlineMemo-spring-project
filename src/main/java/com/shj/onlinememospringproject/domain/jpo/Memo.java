package com.shj.onlinememospringproject.domain.jpo;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
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

    @Column(name = "memo_date", nullable = false)
    private Timestamp memoDate;

    @Column(name = "is_star", columnDefinition = "TINYINT(1) DEFAULT 0", length = 1, nullable = false)
    private boolean isStar;

    // 참고로 이건 db에 안나타남.
    @OneToMany(mappedBy = "memo")
    private Set<UserAndMemo> userAndMemos = new HashSet<>();  // 나중에 안되면 Set말고 List로 변경하자!


    @Builder
    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}