package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.memo.MemoInviteResponseDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDtos;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;

import java.util.List;

public interface UserAndMemoService {

    List<MemoResponseDto> findMemosByUserId(Long userId);  // userId와 일치하는 사용자의 메모들 리스트를 정렬후 반환 기능.
    List<UserResponseDto> findUsersByMemoId(Long memoId);  // memoId와 일치하는 메모의 사용자들 리스트를 정렬후 반환 기능.
    MemoInviteResponseDto inviteUsersToMemo(UserRequestDtos userRequestDtos, Long memoId);  // 초대할 사용자들 리스트와 memoId를 받아서 특정 메모 1개에 사용자들을 메모에 초대하고, memo와 모든 공동 사용자들 리스트 반환 기능.
}
