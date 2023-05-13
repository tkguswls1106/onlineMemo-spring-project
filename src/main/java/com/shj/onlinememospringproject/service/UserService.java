package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserUpdateNameRequestDto;
import com.shj.onlinememospringproject.dto.user.UserUpdatePwRequestDto;

public interface UserService {

    UserResponseDto findById(Long userId);  // userId로 검색한 사용자 1명 반환 기능.
    void updateName(Long userId, UserUpdateNameRequestDto userUpdateNameRequestDto);  // 해당 userId 사용자의 이름 수정 기능.
    void updatePw(UserUpdatePwRequestDto userUpdatePwRequestDto);  // 사용자의 비밀번호 수정 기능.
    void deleteUser(Long userId);  // 해당 userId의 사용자 삭제 기능. 동시에 해당 사용자의 단독 메모도 함께 삭제함.
}
