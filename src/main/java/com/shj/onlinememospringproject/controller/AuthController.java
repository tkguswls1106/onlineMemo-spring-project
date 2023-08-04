package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.token.TokenDto;
import com.shj.onlinememospringproject.dto.user.*;
import com.shj.onlinememospringproject.util.SecurityUtil;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class AuthController {

    private final AuthService authService;


    @GetMapping("/auth")
    public ResponseEntity isLogin() {  // 로그인 상태 여부 확인
        Long userId = SecurityUtil.getCurrentMemberId();
        UserIdResponseDto userIdResponseDto = new UserIdResponseDto(userId);

        return ResponseData.toResponseEntity(ResponseCode.READ_IS_LOGIN, userIdResponseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity joinUser(@RequestBody UserSignupRequestDto userSignupRequestDto) {  // 회원가입
        UserResponseDto userResponseDto = authService.signup(userSignupRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_USER, userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDto userLoginRequestDto) {  // 로그인
        TokenDto tokenDto = authService.login(userLoginRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.LOGIN_SUCCESS, tokenDto);
    }
    @GetMapping("/login")
    public ResponseEntity getLogin() {  // 로그인 api가 get방식으로 요청올시에
        return ResponseData.toResponseEntity(ResponseCode.GET_LOGIN);
    }

    @PutMapping("/password")
    public ResponseEntity updatePassword(@RequestBody UserUpdatePwRequestDto userUpdatePwRequestDto) {  // 비밀번호 수정
        authService.updatePw(userUpdatePwRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_PASSWORD);
    }
}
