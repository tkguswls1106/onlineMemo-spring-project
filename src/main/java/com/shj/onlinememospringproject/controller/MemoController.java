package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private MemoService memoService;
    @Autowired
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }
}
