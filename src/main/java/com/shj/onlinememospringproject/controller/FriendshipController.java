package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.friendship.FriendshipSendRequestDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
// @RequestMapping("/users/{userId}")
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendshipService friendshipService;


    @PostMapping
    public ResponseEntity sendFriendship(@RequestBody FriendshipSendRequestDto friendshipSendRequestDto) {  // 신규 친구요청 생성 (참고로 여기 friendshipSendRequestDto 안에 senderUserId도 들어있음.)
        friendshipService.sendFriendship(friendshipSendRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_SENDFRIENDSHIP);
    }

//    @GetMapping("/senders")
//    public ResponseEntity findSendersByUserId()

//    @GetMapping
//    public ResponseEntity findFriendsByUserId(@RequestBody FriendshipSendRequestDto friendshipSendRequestDto)
}
