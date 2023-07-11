package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserUpdateNameRequestDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.UserService;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserServiceLogic userServiceLogic;


    @GetMapping("/{userId}")
    public ResponseEntity findUserById(@PathVariable Long userId) {  // 회원정보 조회
        userServiceLogic.checkTokenUser(userId);

        UserResponseDto userResponseDto = userService.findById(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userResponseDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateName(@PathVariable Long userId, @RequestBody UserUpdateNameRequestDto userUpdateNameRequestDto) {  // 회원이름 수정
        userServiceLogic.checkTokenUser(userId);

        userService.updateName(userId, userUpdateNameRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_USER);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {  // 회원 탈퇴
        userServiceLogic.checkTokenUser(userId);

        userService.deleteUser(userId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_USER);
    }

}
