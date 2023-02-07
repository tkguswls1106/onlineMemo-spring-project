package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.user.UserJoinRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping  // /users
    public ResponseEntity joinUser(@RequestBody UserJoinRequestDto userJoinRequestDto) {  // 회원가입
        userService.save(userJoinRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_USER);
    }

    @GetMapping("/{userId}")  // /users/{userId}
    public ResponseEntity findUserById(@PathVariable Long userId) {  // 회원정보 조회
        UserResponseDto userResponseDto = userService.findById(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userResponseDto);
    }

}
