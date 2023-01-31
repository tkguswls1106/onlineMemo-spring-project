package com.shj.onlinememospringproject.domain.jpo;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "user_and_memo")
@Entity
public class UserAndMemo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_memo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "memo_id")
    // @NotFound(action = NotFoundAction.IGNORE)
    private Memo memo;


    @Builder
    public UserAndMemo(User user, Memo memo) {
        this.user = user;
        this.memo = memo;
    }


    // 수정(업데이트) 기능
    // 이는 딱히 사용처가 없어 만들지 않았음.
}
