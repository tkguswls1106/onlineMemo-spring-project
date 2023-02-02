package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.user.UserJoinRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;

public interface UserService {

    Long save(UserJoinRequestDto userJoinRequestDto);  // 신규 사용자 생성하고 userId 반환 기능.
    UserResponseDto findById(Long userId);  // userId로 검색한 사용자 1명 반환 기능.
}
