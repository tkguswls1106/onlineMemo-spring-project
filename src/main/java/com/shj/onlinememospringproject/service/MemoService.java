package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.memo.MemoRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;

import java.util.List;

public interface MemoService {

    Long saveMemo(Long userId, MemoRequestDto memoRequestDto);  // 신규 메모 생성하고 memoId 반환 기능.
    MemoResponseDto findById(Long memoId);  // memoId로 검색한 메모 1개 반환 기능.
}
