package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.memo.*;

public interface MemoService {

    MemoSaveResponseDto saveMemo(Long userId, MemoSaveRequestDto memoSaveRequestDto);  // 신규 메모 생성하고 userId와 memo 반환 기능.
    MemoResponseDto findById(Long memoId);  // memoId로 검색한 메모 1개 반환 기능.
    void updateMemo(Long memoId, MemoUpdateRequestDto memoUpdateRequestDto);  // 해당 memoId의 메모 수정 기능.
    void updateIsStar(Long memoId, MemoUpdateStarRequestDto memoUpdateStarRequestDto);  // 해당 memoId의 즐겨찾기 여부 수정 기능.
    void deleteMemo(Long userId, Long memoId);  // 해당 memoId의 메모 삭제 기능. 만약 개인메모가 아닐 경우에는 메모를 삭제하지 않고 메모그룹 탈퇴로 처리함.
}
