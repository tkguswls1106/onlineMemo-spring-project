package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.service.MemoService;
import com.shj.onlinememospringproject.store.normalstore.MemoStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoServiceLogic implements MemoService {

    private MemoStore memoStore;
    @Autowired
    public MemoServiceLogic(MemoStore memoStore) {
        this.memoStore = memoStore;
    }
}
