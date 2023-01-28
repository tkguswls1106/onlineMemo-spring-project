package com.shj.onlinememospringproject.store.repository;

import com.shj.onlinememospringproject.store.jpastore.MemoJpaStore;
import com.shj.onlinememospringproject.store.normalstore.MemoStore;
import org.springframework.stereotype.Repository;

@Repository
public class MemoRepository implements MemoStore {

    private MemoJpaStore memoJpaStore;
    public MemoRepository(MemoJpaStore memoJpaStore){
        this.memoJpaStore = memoJpaStore;
    }
}
