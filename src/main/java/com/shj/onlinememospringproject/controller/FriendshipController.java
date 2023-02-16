package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.friendship.FriendshipSendRequestDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipUpdateRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/users/{userId}")
public class FriendshipController {

    private final FriendshipService friendshipService;


    @PostMapping("/friends")
    public ResponseEntity sendFriendship(@PathVariable Long userId, @RequestBody FriendshipSendRequestDto friendshipSendRequestDto) {  // 신규 친구요청 생성
        friendshipService.sendFriendship(userId, friendshipSendRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_SENDFRIENDSHIP);  // 이건 굳이 반환데이터를 보여줄 필요가 없을것같아서 void로 만들고 보여주지 않겠음.
    }

    @GetMapping("/senders")
    public ResponseEntity findSendersByUserId(@PathVariable Long userId) {  // 해당 userId 사용자의 친구요청 응답대기중인 요청발신자 사용자들 리스트 조회
        List<UserResponseDto> userResponseDtos = friendshipService.findSendersByUserId(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_SENDERLIST, userResponseDtos);
    }

    @GetMapping("/friends")
    public ResponseEntity findFriendsByUserId(@PathVariable Long userId) {  // 해당 userId 사용자의 친구들 리스트 조회
        List<UserResponseDto> userResponseDtos = friendshipService.findFriendsByUserId(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_FRIENDLIST, userResponseDtos);
    }

    @PutMapping("/senders/{senderUserId}")
    public ResponseEntity updateFriendship(@PathVariable Long userId, @PathVariable Long senderUserId, @RequestBody FriendshipUpdateRequestDto friendshipUpdateRequestDto) {  // 신청된 친구요청에 대한 친구 맺기 여부 수정
        friendshipService.updateFriendship(userId, senderUserId, friendshipUpdateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_FRIENDSHIP);
    }

    @DeleteMapping("/friends/{senderUserId}")
    public ResponseEntity deleteFriendship(@PathVariable Long userId, @PathVariable Long senderUserId) {  // 친구상태 관계 해지
        friendshipService.deleteFriendship(userId, senderUserId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_FRIENDSHIP);
    }

}
