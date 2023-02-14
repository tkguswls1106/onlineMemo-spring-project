package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.friendship.FriendshipResponseDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipSendRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;

import java.util.List;

public interface FriendshipService {

    void sendFriendship(Long senderUserId, FriendshipSendRequestDto friendshipSendRequestDto);  // 친구요청 보내기 기능.
    List<UserResponseDto> findSendersByUserId(Long userId);  // 해당 userId 사용자의 친구요청 응답대기중인 요청발신자 사용자들 리스트 반환 기능.
    List<UserResponseDto> findFriendsByUserId(Long userId);  // 해당 userId 사용자의 친구들 리스트 반환 기능.
}
