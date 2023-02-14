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

    // 초기 친구요청 상태 => isFriend:0, isWait:1
    // 친구요청 수락 상태 => isFriend:1, isWait:0 => 친구 맺기 성공
    // 친구요청 거절 상태 => isFriend:0, isWait:0 => 친구 맺기 실패 => Friendship DB 테이블에서 해당 컬럼 삭제 조치.
    // isFriend:1, isWait:1 => 에러임. 예외처리 조치.

    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
    public void onPrePersist(){
        this.isFriend = this.isFriend == null ? 0 : this.isFriend;
        this.isWait = this.isWait == null ? 1 : this.isWait;
    }
}