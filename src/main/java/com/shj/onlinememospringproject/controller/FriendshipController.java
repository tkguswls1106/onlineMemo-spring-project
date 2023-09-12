package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.friendship.FriendshipSendRequestDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipSendResponseDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipUpdateRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.service.FriendshipService;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@Api(tags = {"Friendship"})
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/users/{userId}")
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final UserServiceLogic userServiceLogic;


    @ApiOperation(value = "신규 친구요청", notes = "친구요청을 보냅니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=201가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 201, message = MessageItem.CREATED_SENDFRIENDSHIP, response = FriendshipSendResponseDto.class)
    })
    @PostMapping("/friends")
    public ResponseEntity sendFriendship(@PathVariable Long userId, @RequestBody FriendshipSendRequestDto friendshipSendRequestDto) {  // 신규 친구요청 생성
        userServiceLogic.checkTokenUser(userId);

        FriendshipSendResponseDto friendshipSendResponseDto = friendshipService.sendFriendship(userId, friendshipSendRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_SENDFRIENDSHIP, friendshipSendResponseDto);
    }

    @ApiOperation(value = "친구요청 수신목록 조회", notes = "친구요청을 수신한 목록을 조회합니다. 반환된 데이터는 '여러 객체를 담은 리스트' 형식입니다. (jwt O)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.READ_SENDERLIST, response = UserResponseDto.class, responseContainer = "List")})
    @GetMapping("/senders")
    public ResponseEntity findSendersByUserId(@PathVariable Long userId) {  // 해당 userId 사용자의 친구요청 응답대기중인 요청발신자 사용자들 리스트 조회
        userServiceLogic.checkTokenUser(userId);

        List<UserResponseDto> userResponseDtos = friendshipService.findSendersByUserId(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_SENDERLIST, userResponseDtos);
    }

    @ApiOperation(value = "친구 목록 조회", notes = "친구 목록을 조회합니다. 반환된 데이터는 '여러 객체를 담은 리스트' 형식입니다. (jwt O)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.READ_FRIENDLIST, response = UserResponseDto.class, responseContainer = "List")})
    @GetMapping("/friends")
    public ResponseEntity findFriendsByUserId(@PathVariable Long userId) {  // 해당 userId 사용자의 친구들 리스트 조회
        userServiceLogic.checkTokenUser(userId);

        List<UserResponseDto> userResponseDtos = friendshipService.findFriendsByUserId(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_FRIENDLIST, userResponseDtos);
    }

    @ApiOperation(value = "친구요청 수락 또는 거절", notes = "친구요청을 수락 또는 거절합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.UPDATE_FRIENDSHIP + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @PutMapping("/senders/{senderUserId}")
    public ResponseEntity updateFriendship(@PathVariable Long userId, @PathVariable Long senderUserId, @RequestBody FriendshipUpdateRequestDto friendshipUpdateRequestDto) {  // 신청된 친구요청에 대한 친구 맺기 여부 수정
        userServiceLogic.checkTokenUser(userId);

        friendshipService.updateFriendship(userId, senderUserId, friendshipUpdateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_FRIENDSHIP);
    }

    @ApiOperation(value = "친구 삭제", notes = "친구 관계를 해지합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.DELETE_FRIENDSHIP + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @DeleteMapping("/friends/{senderUserId}")
    public ResponseEntity deleteFriendship(@PathVariable Long userId, @PathVariable Long senderUserId) {  // 친구상태 관계 해지
        userServiceLogic.checkTokenUser(userId);

        friendshipService.deleteFriendship(userId, senderUserId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_FRIENDSHIP);
    }

}
