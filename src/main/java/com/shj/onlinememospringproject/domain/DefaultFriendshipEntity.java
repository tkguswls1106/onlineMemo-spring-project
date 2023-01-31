package com.shj.onlinememospringproject.domain;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DefaultFriendshipEntity {

    @Column(name = "is_friend", columnDefinition = "TINYINT(1) default 0", length = 1)
    protected Integer isFriend;

    @Column(name = "is_wait", columnDefinition = "TINYINT(1) default 1", length = 1)
    protected Integer isWait;

    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
    public void onPrePersist(){
        this.isFriend = this.isFriend == null ? 0 : this.isFriend;
        this.isWait = this.isWait == null ? 1 : this.isWait;
    }
}