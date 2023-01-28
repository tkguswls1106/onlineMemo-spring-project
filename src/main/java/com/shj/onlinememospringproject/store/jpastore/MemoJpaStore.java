package com.shj.onlinememospringproject.store.jpastore;

import com.shj.onlinememospringproject.domain.jpo.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoJpaStore extends JpaRepository<Memo, Long> {
}
