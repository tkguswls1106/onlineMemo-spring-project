package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.memo.MemoInviteResponseDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoResponseDto;

import java.util.List;

public interface UserAndMemoService {

    List<MemoResponseDto> findMemosByUserId(Long userId);  // userId와 일치하는 사용자의 메모들 리스트를 정렬후 반환 기능.
    List<UserResponseDto> findUsersByMemoId(Long memoId);  // memoId와 일치하는 메모의 사용자들 리스트를 정렬후 반환 기능.
    //List<UserResponseDto> inviteUserToMemo(Long userId, Long memoId);  // memoId와 userId를 받아 특정 메모 1개에 친구(사용자) 1명을 메모에 초대하고 모든 공동 사용자들 리스트 반환 기능.
    MemoInviteResponseDto inviteUsersToMemo(List<UserRequestDto> userRequestDtos, Long memoId);  // 초대할 사용자들 리스트와 memoId를 받아서 특정 메모 1개에 사용자들을 메모에 초대하고, memo와 모든 공동 사용자들 리스트 반환 기능.



    // 이건 테스트용
    List<UserAndMemoResponseDto> findAllUserAndMemo(Long userId, Long memoId);  // 사용자와 메모의 관계 상황을 반환 기능.
}
