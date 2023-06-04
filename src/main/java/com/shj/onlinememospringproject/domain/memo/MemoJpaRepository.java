package com.shj.onlinememospringproject.domain.memo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface MemoJpaRepository extends JpaRepository<Memo, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update memo m set m.is_star = :star where m.memo_id = :mid", nativeQuery = true)
    void updateStar(@Param("mid") Long id, @Param("star") Integer isStar);  // 메모 즐겨찾기 여부 수정 기능.
    // 이는 따로 MemoJpaRepository 인터페이스에 @Query로 updateStar메소드를 따로 작성해주었다. 그 이유는 DefaultMemoEntity 클래스에 주석으로 첨부하였음.
}
