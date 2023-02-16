package com.shj.onlinememospringproject.service;

import com.shj.onlinememospringproject.dto.friendship.FriendshipResponseDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipSendRequestDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipSendResponseDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipUpdateRequestDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;

import java.util.List;

public interface FriendshipService {

    FriendshipSendResponseDto sendFriendship(Long senderUserId, FriendshipSendRequestDto friendshipSendRequestDto);  // 친구요청 보내기 기능.
    List<UserResponseDto> findSendersByUserId(Long userId);  // 해당 userId 사용자의 친구요청 응답대기중인 요청발신자 사용자들 리스트 반환 기능.
    List<UserResponseDto> findFriendsByUserId(Long userId);  // 해당 userId 사용자의 친구들 리스트 반환 기능.
    void updateFriendship(Long userId, Long senderUserId, FriendshipUpdateRequestDto friendshipUpdateRequestDto);  // 친구요청 수신자는 userId, 발신자는 senderUserId에 해당되는 요청 친구에 대한 친구 맺기 여부 수정 기능.
    void deleteFriendship(Long userId, Long senderUserId);  // 해당 userId 사용자의 친구들중에서 해당 senderUserId의 사용자를 친구 목록에서 삭제 기능.
}
