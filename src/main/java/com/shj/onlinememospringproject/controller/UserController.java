package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserUpdateNameRequestDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.service.UserService;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@Api(tags = {"User"})
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserServiceLogic userServiceLogic;


    @ApiOperation(value = "회원정보 조회", notes = "회원 1명의 정보를 조회합니다. (jwt O)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.READ_USER, response = UserResponseDto.class)})
    @GetMapping("/{userId}")
    public ResponseEntity findUserById(@PathVariable Long userId) {  // 회원정보 조회
        userServiceLogic.checkTokenUser(userId);

        UserResponseDto userResponseDto = userService.findById(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userResponseDto);
    }

    @ApiOperation(value = "회원이름 수정", notes = "회원 1명의 이름을 수정합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.UPDATE_USER + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @PutMapping("/{userId}")
    public ResponseEntity updateName(@PathVariable Long userId, @RequestBody UserUpdateNameRequestDto userUpdateNameRequestDto) {  // 회원이름 수정
        userServiceLogic.checkTokenUser(userId);

        userService.updateName(userId, userUpdateNameRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_USER);
    }

    @ApiOperation(value = "회원정보 삭제", notes = "회원 1명을 탈퇴시킵니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.DELETE_USER + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {  // 회원 탈퇴
        userServiceLogic.checkTokenUser(userId);

        userService.deleteUser(userId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_USER);
    }

}
